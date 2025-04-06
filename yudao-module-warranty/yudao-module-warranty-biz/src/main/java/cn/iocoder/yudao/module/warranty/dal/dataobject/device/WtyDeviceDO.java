package cn.iocoder.yudao.module.warranty.dal.dataobject.device;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备 DO
 *
 * @author Remay
 */
@TableName("warranty_device")
@KeySequence("warranty_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WtyDeviceDO extends BaseDO {

    /**
     * ID主键
     */
    @TableId
    private Long id;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备编号
     */
    private String deviceCode;
    /**
     * 设备描述
     */
    private String description;
    /**
     * 设备类型ID
     */
    private Long typeId;
    /**
     * 所属机构ID
     */
    private Long deptId;
    /**
     * 设备状态（0：停用，1：启用）
     *
     * 枚举 {@link TODO warranty_device_status 对应的类}
     */
    private Boolean status;
    /**
     * 是否审核（0：未审核，1：已通过，2：未通过）
     *
     * 枚举 {@link TODO warranty_audit 对应的类}
     */
    private Integer isAudit;

}