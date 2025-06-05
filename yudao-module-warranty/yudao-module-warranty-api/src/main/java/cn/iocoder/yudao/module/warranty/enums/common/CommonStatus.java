package cn.iocoder.yudao.module.warranty.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lsy79
 * @version 1.0
 * @description: 通用状态
 * @date 2025/4/7 19:33
 */
@Getter
@AllArgsConstructor
public enum CommonStatus {

    UNABLE(0),
    ENABLE(1)
    ;

    private final Integer status;
}
