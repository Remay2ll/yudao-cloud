package cn.iocoder.yudao.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部门类型枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum DeptTypeEnum {

    INTERNAL(1, "内部单位"),
    BANK(2, "银行单位"),
    DISTRICT(3, "分区");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String description;

} 