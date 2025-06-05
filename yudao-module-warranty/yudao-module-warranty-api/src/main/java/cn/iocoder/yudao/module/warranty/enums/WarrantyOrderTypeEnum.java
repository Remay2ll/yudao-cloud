package cn.iocoder.yudao.module.warranty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 工单类型枚举
 * <p>
 * 用于区分工单的创建来源和基本性质。
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum WarrantyOrderTypeEnum {

    CUSTOMER_CREATED(0, "客户创建单"), // 由客户主动创建的工单，通常为维修请求
    AGREEMENT_GENERATED(1, "协议生成单"); // 由维保协议自动生成的工单，例如定期巡检、计划内维修等

    /**
     * 类型码
     */
    private final Integer code;
    /**
     * 类型描述
     */
    private final String description;

    // Optional: 可以添加一个静态方法根据 code 获取枚举，方便使用
    public static WarrantyOrderTypeEnum getByCode(Integer code) {
        for (WarrantyOrderTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null; // 或者抛出异常
    }
} 