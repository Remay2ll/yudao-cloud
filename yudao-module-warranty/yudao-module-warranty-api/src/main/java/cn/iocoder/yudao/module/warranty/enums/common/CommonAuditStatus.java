package cn.iocoder.yudao.module.warranty.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lsy79
 * @version 1.0
 * @description: 通用审批状态
 * @date 2025/4/7 19:34
 */

@Getter
@AllArgsConstructor
public enum CommonAuditStatus {

    PENDING(0),
    APPROVED(1),
    REJECTED(2)
    ;
    private final Integer auditStatus;
}
