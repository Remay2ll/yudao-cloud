package cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorder;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单配件 DO
 *
 * @author Remay
 */
@TableName("warranty_mainten_order_part")
@KeySequence("warranty_mainten_order_part_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenOrderPartDO extends BaseDO {

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
     * 配件ID
     */
    private Long partId;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 关于此配件在此工单中的备注
     */
    private String remark;

}