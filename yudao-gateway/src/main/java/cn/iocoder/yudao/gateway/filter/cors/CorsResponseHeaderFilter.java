package cn.iocoder.yudao.gateway.filter.cors;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * 解决 Spring Cloud Gateway 2.x 跨域时，出现重复 Origin 的 BUG
 *
 * 参考文档：<a href="https://blog.csdn.net/zimou5581/article/details/90043178" />
 *
 * @author 芋道源码
 */
@Component
public class CorsResponseHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        // 指定此过滤器位于 NettyWriteResponseFilter 之后
        // 即待处理完响应体后接着处理响应头
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*
         * MODIFICATION START
         * 旧代码问题:
         * 1. 使用 Mono.defer(() -> {}) 时，lambda 表达式的返回类型 (Mono<Void>) 与 Mono.defer 所期望的 Supplier<? extends Mono<? extends T>> 不完全匹配，导致编译错误。
         * 2. 在 Mono.defer 的 lambda 表达式中，错误地再次调用了 chain.filter(exchange)，这会重新发起过滤链，逻辑上不正确。
         * 3. 匿名内部类创建列表的方式 (new ArrayList<String>() {{ add(kv.getValue().get(0)); }}) 虽然有效，但可读性稍差。
         *
         * 修改方案:
         * 1. 将 Mono.defer() 替换为 Mono.fromRunnable()，用于在响应链处理完毕后执行同步的副作用操作（修改响应头）。
         * 2. 移除了 Mono.defer() 内部冗余的 chain.filter(exchange) 调用。
         * 3. 将匿名内部类的列表创建方式修改为更常规和清晰的 ArrayList 实例化和添加元素。
         * MODIFICATION END
         */
        return chain.filter(exchange).then(Mono.fromRunnable(() -> { // 修改点1: Mono.defer() -> Mono.fromRunnable()
            exchange.getResponse().getHeaders().entrySet().stream()
                    .filter(kv -> (kv.getValue() != null && kv.getValue().size() > 1))
                    .filter(kv -> (kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
                            || kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS)))
                    .forEach(kv -> {
                        // 旧代码: kv.setValue(new ArrayList<String>() {{ add(kv.getValue().get(0)); }});
                        ArrayList<String> singleValueList = new ArrayList<>(); // 修改点3: 列表创建方式
                        singleValueList.add(kv.getValue().get(0));
                        kv.setValue(singleValueList);
                    });
            // 修改点2: 移除了此处冗余的 return chain.filter(exchange);
        }));
    }

}
