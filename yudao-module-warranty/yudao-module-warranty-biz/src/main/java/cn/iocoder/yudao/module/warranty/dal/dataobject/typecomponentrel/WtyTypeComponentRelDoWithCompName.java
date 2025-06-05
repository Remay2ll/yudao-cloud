package cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel;

import lombok.Data;

/**
 * @author lsy79
 * @version 1.0
 * @description: 设备类型和配件关联DO带配件名称
 * @date 2025/4/9 1:22
 */
@Data
public class WtyTypeComponentRelDoWithCompName extends WtyTypeComponentRelDO {
    private String componentName;
}
