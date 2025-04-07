package cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备类型-配件关联 DO
 *
 * @author Remay
 */
@TableName("warranty_type_component_rel")
@KeySequence("warranty_type_component_rel_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WtyTypeComponentRelDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 设备类型ID
     */
    private Long typeId;
    /**
     * 配件ID
     */
    private Long componentId;
    /**
     * 配件数量
     */
    private Integer quantity;

}