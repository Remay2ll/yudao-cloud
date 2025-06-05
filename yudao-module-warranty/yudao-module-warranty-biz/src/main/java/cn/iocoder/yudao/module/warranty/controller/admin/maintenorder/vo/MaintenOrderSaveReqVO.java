package cn.iocoder.yudao.module.warranty.controller.admin.maintenorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice.MaintenOrderDeviceDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderpart.MaintenOrderPartDO;

@Schema(description = "管理后台 - 维修工单新增/修改 Request VO")
@Data
public class MaintenOrderSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "工单标题/简述")
    private String title;

    @Schema(description = "优先级 (例如 0:低, 1:中, 2:高)")
    private Integer priority;

    @Schema(description = "客户ID", example = "22932")
    private Long customerId;

    @Schema(description = "客户名称", example = "张三")
    private String customerName;

    @Schema(description = "客户联系人", example = "王五")
    private String customerContactName;

    @Schema(description = "客户联系电话")
    private String customerContactPhone;

    @Schema(description = "客户地址/报修地址")
    private String customerAddress;

    @Schema(description = "问题描述/维保前描述", example = "你猜")
    private String problemDescription;

    @Schema(description = "报修人用户ID", example = "28377")
    private Long reportedByUserId;

    @Schema(description = "负责维保公司ID", example = "7787")
    private Long maintenanceCompanyId;

    @Schema(description = "指派维修工用户ID", example = "4145")
    private Long assignedToUserId;

    @Schema(description = "指派维修工姓名", example = "芋艿")
    private String assignedToUserName;

    @Schema(description = "分派时间")
    private LocalDateTime assignmentTime;

    @Schema(description = "接单时间")
    private LocalDateTime acceptanceTime;

    @Schema(description = "计划开始时间")
    private LocalDateTime scheduledStartTime;

    @Schema(description = "计划结束时间")
    private LocalDateTime scheduledEndTime;

    @Schema(description = "实际开始时间/维保开始时间")
    private LocalDateTime actualStartTime;

    @Schema(description = "实际完成时间/完工时间")
    private LocalDateTime actualEndTime;

    @Schema(description = "签到地址")
    private String checkInLocation;

    @Schema(description = "签退地址")
    private String checkOutLocation;

    @Schema(description = "工作总结/维保后描述")
    private String workSummary;

    @Schema(description = "维保前文件")
    private String filesBeforeMaintenance;

    @Schema(description = "维保后文件")
    private String filesAfterMaintenance;

    @Schema(description = "维修员工签字文件")
    private String technicianSignatureFile;

    @Schema(description = "客户签字文件")
    private String customerSignatureFile;

    @Schema(description = "拒绝时间 (维修工拒绝)")
    private LocalDateTime refuseTime;

    @Schema(description = "拒绝原因", example = "不香")
    private String refuseReason;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因", example = "不好")
    private String cancelReason;

    @Schema(description = "取消操作人用户ID", example = "28989")
    private Long cancelledByUserId;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "设备所属单位ID", example = "15807")
    private Long owningDeptId;

    @Schema(description = "服务片区部门ID", example = "27592")
    private Long serviceAreaDeptId;

    @Schema(description = "故障类型ID", example = "1001")
    private Long issueTypeId;

    @Schema(description = "故障类型名称", example = "硬件故障")
    private String issueTypeName;

    @Schema(description = "预计处理时长(分钟)", example = "120")
    private Integer estimatedDuration;

    @Schema(description = "维修工单设备关联列表")
    private List<MaintenOrderDeviceDO> maintenOrderDevices;

    @Schema(description = "工单配件列表")
    private List<MaintenOrderPartDO> maintenOrderParts;

}