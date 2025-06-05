package cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 维修工单设备关联 DO
 *
 * @author Remay
 */
@TableName("warranty_mainten_order_device")
@KeySequence("warranty_mainten_order_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenOrderDeviceDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 维修工单ID
     */
    private Long workOrderId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 关于此设备在此工单中的备注
     */
    private String remark;

}