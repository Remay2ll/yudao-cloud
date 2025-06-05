package cn.iocoder.yudao.module.warranty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * @author lsy79
 * @version 1.0
 * @description: Warranty模块启动类
 * @date 2025/4/3 17:50
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "cn.iocoder.yudao.module") // 扫描 Feign 客户端
@MapperScan("cn.iocoder.yudao.module.warranty.dal.mysql") // 扫描 MyBatis Mapper
public class WarrantyServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WarrantyServerApplication.class, args);
    }
}
