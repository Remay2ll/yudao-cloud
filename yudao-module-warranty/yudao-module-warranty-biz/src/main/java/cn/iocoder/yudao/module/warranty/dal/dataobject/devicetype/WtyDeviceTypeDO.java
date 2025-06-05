package cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备类型 DO
 *
 * @author Remay
 */
@TableName("warranty_device_type")
@KeySequence("warranty_device_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WtyDeviceTypeDO extends BaseDO {

    public static final Long PARENT_TYPE_ID_ROOT = 0L;
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 类型编码
     */
    private String typeCode;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 父类型ID
     */
    private Long parentTypeId;
    /**
     * 是否是叶子节点（0：否，1：是）
     */
    private Integer isLeaf;
    /**
     * 类型描述
     */
    private String description;
    /**
     * 状态
     *
     * 枚举 {@link TODO warranty_device_type_status 对应的类}
     */
    private Integer status;
    /**
     * 是否审核
     *
     * 枚举 {@link TODO warranty_audit 对应的类}
     */
    private Integer isAudit;

}