package cn.iocoder.yudao.module.warranty.dal.dataobject.issuetype;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 故障类型 DO
 *
 * @author Remay
 */
@TableName("warranty_issue_type")
@KeySequence("warranty_issue_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueTypeDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * ID主键
     */
    @TableId
    private Long id;
    /**
     * 故障类型编码
     */
    private String typeCode;
    /**
     * 故障类型名称
     */
    private String typeName;
    /**
     * 故障类型描述
     */
    private String description;
    /**
     * 父级故障类型ID（0表示顶级分类）
     */
    private Long parentId;
    /**
     * 默认优先级（1-高，2-中，3-低）
     */
    private Integer priorityLevel;
    /**
     * 预计处理时长（分钟）
     */
    private Integer expectedDuration;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 状态（0：停用，1：启用）
     */
    private Boolean status;
    /**
     * 是否审核（0：未审核，1：已通过，2：未通过）
     */
    private Integer isAudit;

}