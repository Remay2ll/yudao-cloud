package cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorder;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 维修工单 DO
 *
 * @author Remay
 */
@TableName("warranty_mainten_order")
@KeySequence("warranty_mainten_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenOrderDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 工单编号
     */
    private String workOrderNo;
    /**
     * 工单标题/简述
     */
    private String title;
    /**
     * 工单类型 (例如 0:客户创建单, 1:协议生成单)
     *
     * 枚举 {@link cn.iocoder.yudao.module.warranty.enums.WarrantyOrderTypeEnum}
     */
    private Integer orderType;
    /**
     * 工单状态 (例如 0:待派发, 1:已派发, 2:已接单, 3:进行中, 4:已完工, 5:已取消, 6:已拒绝)
     *
     * 枚举 {@link cn.iocoder.yudao.module.warranty.enums.WarrantyOrderStatusEnum}
     */
    private Integer orderStatus;
    /**
     * 优先级 (例如 0:低, 1:中, 2:高)
     *
     * 枚举 {@link cn.iocoder.yudao.module.warranty.enums.WarrantyOrderPriorityEnum}
     */
    private Integer priority;
    /**
     * 关联维保协议ID
     */
    private Long agreementId;
    /**
     * 关联维保协议号
     */
    private String agreementNo;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户联系人
     */
    private String customerContactName;
    /**
     * 客户联系电话
     */
    private String customerContactPhone;
    /**
     * 客户地址/报修地址
     */
    private String customerAddress;
    /**
     * 问题描述/维保前描述
     */
    private String problemDescription;
    /**
     * 报修人用户ID
     */
    private Long reportedByUserId;
    /**
     * 报修时间
     */
    private LocalDateTime reportTime;
    /**
     * 负责维保公司ID
     */
    private Long maintenanceCompanyId;
    /**
     * 指派维修工用户ID
     */
    private Long assignedToUserId;
    /**
     * 指派维修工姓名
     */
    private String assignedToUserName;
    /**
     * 分派时间
     */
    private LocalDateTime assignmentTime;
    /**
     * 接单时间
     */
    private LocalDateTime acceptanceTime;
    /**
     * 计划开始时间
     */
    private LocalDateTime scheduledStartTime;
    /**
     * 计划结束时间
     */
    private LocalDateTime scheduledEndTime;
    /**
     * 实际开始时间/维保开始时间
     */
    private LocalDateTime actualStartTime;
    /**
     * 实际完成时间/完工时间
     */
    private LocalDateTime actualEndTime;
    /**
     * 签到地址
     */
    private String checkInLocation;
    /**
     * 签退地址
     */
    private String checkOutLocation;
    /**
     * 工作总结/维保后描述
     */
    private String workSummary;
    /**
     * 维保前文件
     */
    private String filesBeforeMaintenance;
    /**
     * 维保后文件
     */
    private String filesAfterMaintenance;
    /**
     * 维修员工签字文件
     */
    private String technicianSignatureFile;
    /**
     * 客户签字文件
     */
    private String customerSignatureFile;
    /**
     * 拒绝时间 (维修工拒绝)
     */
    private LocalDateTime refuseTime;
    /**
     * 拒绝原因
     */
    private String refuseReason;
    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;
    /**
     * 取消原因
     */
    private String cancelReason;
    /**
     * 取消操作人用户ID
     */
    private Long cancelledByUserId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 设备所属单位ID
     */
    private Long owningDeptId;
    /**
     * 服务片区部门ID
     */
    private Long serviceAreaDeptId;
    /**
     * 故障类型ID (关联 warranty_issue_type.id)
     */
    private Long issueTypeId;
    /**
     * 故障类型名称 (冗余字段，便于查询)
     */
    private String issueTypeName;
    /**
     * 预计处理时长(分钟，基于故障类型自动填充)
     */
    private Integer estimatedDuration;

}