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
    // ========== COMPONENT 模块 1-006-002-000 ==========
    ErrorCode WTY_COMPONENT_NOT_EXISTS = new ErrorCode(1_006_002_000, "配件不存在");
    // ========== DEVICE-TYPE-COMPONENT_REL 模块 1-006-003-000 ==========
    ErrorCode WTY_TYPE_COMPONENT_REL_NOT_EXISTS = new ErrorCode(1_006_003_000, "设备类型-配件关联不存在");
}
