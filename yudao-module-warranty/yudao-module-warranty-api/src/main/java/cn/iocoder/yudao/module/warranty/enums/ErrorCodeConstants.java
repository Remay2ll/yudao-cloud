package cn.iocoder.yudao.module.warranty.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Warranty 错误码枚举类
 *
 * Warranty 系统，使用 1-006-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== DEVICE 模块 1-006-000-000 ==========
    ErrorCode WTY_DEVICE_NOT_EXISTS = new ErrorCode(1_006_000_000, "设备不存在");
    // ========== DEVICE-TYPE 模块 1-006-001-000 ==========
    ErrorCode WTY_DEVICE_TYPE_NOT_EXISTS = new ErrorCode(1_006_001_000, "设备类型不存在");
    ErrorCode WTY_DEVICE_TYPE_EXITS_CHILDREN = new ErrorCode(1_006_001_001, "存在存在子设备类型，无法删除");
    ErrorCode WTY_DEVICE_TYPE_PARENT_NOT_EXITS = new ErrorCode(1_006_001_002,"父级设备类型不存在");
    ErrorCode WTY_DEVICE_TYPE_PARENT_ERROR = new ErrorCode(1_006_001_003, "不能设置自己为父设备类型");
    ErrorCode WTY_DEVICE_TYPE_TYPE_NAME_DUPLICATE = new ErrorCode(1_006_001_004, "已经存在该类型名称的设备类型");
    ErrorCode WTY_DEVICE_TYPE_PARENT_IS_CHILD = new ErrorCode(1_006_001_005, "不能设置自己的子WtyDeviceType为父WtyDeviceType");
    // ========== COMPONENT 模块 1-006-002-000 ==========
    ErrorCode WTY_COMPONENT_NOT_EXISTS = new ErrorCode(1_006_002_000, "配件不存在");
    // ========== DEVICE-TYPE-COMPONENT_REL 模块 1-006-003-000 ==========
    ErrorCode WTY_TYPE_COMPONENT_REL_NOT_EXISTS = new ErrorCode(1_006_003_000, "设备类型-配件关联不存在");
    // ========== DEPT 模块 1-006-004-000 ==========
    ErrorCode DEPT_NOT_EXISTS = new ErrorCode(1_006_004_000, "部门不存在");
    // ========== DEPT-ADDRESS 模块 1-006-005-000 ==========
    ErrorCode DEPT_ADDRESS_NOT_EXISTS = new ErrorCode(1_006_005_000, "部门地址不存在");
    // ========== MAINTAIN-ORDER 模块 1-006-006-000 ==========
    ErrorCode MAINTEN_ORDER_NOT_EXISTS = new ErrorCode(1_006_006_000, "维修工单不存在");
    // ========== MAINTAIN-ORDER-PART 工单配件 1-006-007-000 ==========
    ErrorCode MAINTEN_ORDER_PART_NOT_EXISTS = new ErrorCode(1_006_007_000, "工单配件不存在");
    // ========== MAINTAIN-ORDER-DEVICE 维修工单设备关联 1-006-008-000 ==========
    ErrorCode MAINTEN_ORDER_DEVICE_NOT_EXISTS = new ErrorCode(1_006_008_000, "维修工单设备关联不存在");
    // ========== MAINTAIN-ISSUE-TYPE 维修工单问题类型 1-006-009-000 ==========
    ErrorCode ISSUE_TYPE_NOT_EXISTS = new ErrorCode(1_006_009_000, "故障类型不存在");
    ErrorCode ISSUE_TYPE_EXITS_CHILDREN = new ErrorCode(1_006_009_001, "存在存在子故障类型，无法删除");
    ErrorCode ISSUE_TYPE_PARENT_NOT_EXITS = new ErrorCode(1_006_009_002,"父级故障类型不存在");
    ErrorCode ISSUE_TYPE_PARENT_ERROR = new ErrorCode(1_006_009_003, "不能设置自己为父故障类型");
    ErrorCode ISSUE_TYPE_TYPE_NAME_DUPLICATE = new ErrorCode(1_006_009_004, "已经存在该故障类型名称的故障类型");
    ErrorCode ISSUE_TYPE_PARENT_IS_CHILD = new ErrorCode(1_006_009_005, "不能设置自己的子IssueType为父IssueType");
}
