package cn.iocoder.yudao.module.warranty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 工单优先级枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum WarrantyOrderPriorityEnum {

    LOW(0, "低"),
    MEDIUM(1, "中"),
    HIGH(2, "高");

    /**
     * 优先级码
     */
    private final Integer code;
    /**
     * 优先级描述
     */
    private final String description;

    // Optional: 可以添加一个静态方法根据 code 获取枚举，方便使用
    public static WarrantyOrderPriorityEnum getByCode(Integer code) {
        for (WarrantyOrderPriorityEnum priority : values()) {
            if (priority.getCode().equals(code)) {
                return priority;
            }
        }
        return null; // 或者抛出异常
    }
} 