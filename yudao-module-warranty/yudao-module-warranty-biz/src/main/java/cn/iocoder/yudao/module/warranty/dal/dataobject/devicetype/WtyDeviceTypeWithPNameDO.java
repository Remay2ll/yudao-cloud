package cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype;

import lombok.Data;


/**
 * @author lsy79
 * @version 1.0
 * @description: 包含父节点名称的设备类型
 * @date 2025/4/7 13:08
 */

@Data
public class WtyDeviceTypeWithPNameDO extends WtyDeviceTypeDO {
    private String parentTypeName;
}
