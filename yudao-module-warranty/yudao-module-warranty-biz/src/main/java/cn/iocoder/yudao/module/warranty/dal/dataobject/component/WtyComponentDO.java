package cn.iocoder.yudao.module.warranty.dal.dataobject.component;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 配件 DO
 *
 * @author Remay
 */
@TableName("warranty_component")
@KeySequence("warranty_component_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WtyComponentDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 配件编号
     */
    private String componentCode;
    /**
     * 配件名称
     */
    private String componentName;
    /**
     * 单位
     *
     * 枚举 {@link TODO warranty_component_unit 对应的类}
     */
    private String unit;
    /**
     * 备注
     */
    private String remark;
    /**
     * 配件维修价格
     */
    private BigDecimal priceMaintain;
    /**
     * 配件更换价格
     */
    private BigDecimal priceChange;
    /**
     * 设备状态
     *
     * 枚举 {@link TODO warranty_component_status 对应的类}
     */
    private Boolean status;
    /**
     * 是否审核
     *
     * 枚举 {@link TODO warranty_audit 对应的类}
     */
    private Integer isAudit;

}