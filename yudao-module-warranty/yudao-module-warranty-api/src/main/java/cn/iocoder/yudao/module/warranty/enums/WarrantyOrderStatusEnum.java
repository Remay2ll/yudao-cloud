package cn.iocoder.yudao.module.warranty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 工单状态枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum WarrantyOrderStatusEnum {

    PENDING_DISPATCH(0, "待派发"),
    DISPATCHED(1, "已派发"),
    ACCEPTED(2, "已接单"),
    IN_PROGRESS(3, "进行中"),
    WORK_COMPLETED(4, "已完工"), // 维修员完成工作
    CANCELLED(5, "已取消"),
    REJECTED_BY_TECHNICIAN(6, "已拒绝"), // 维修员拒绝

    PENDING_APPROVAL(7, "待审批"), // 管理员审批 (如果流程需要)
    APPROVAL_REJECTED(8, "审批不通过"), // 管理员审批不通过 (如果流程需要)

    PENDING_EVALUATION(9, "待评价"), // 客户评价
    CLOSED(10, "已关闭"); // 工单最终关闭状态

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态描述
     */
    private final String description;

    // Optional: 可以添加一个静态方法根据 code 获取枚举，方便使用
    public static WarrantyOrderStatusEnum getByCode(Integer code) {
        for (WarrantyOrderStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null; // 或者抛出异常
    }
} 