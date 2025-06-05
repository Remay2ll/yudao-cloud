package cn.iocoder.yudao.module.warranty.controller.admin.maintenorder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 维修工单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaintenOrderPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "工单编号")
    private String workOrderNo;

    @Schema(description = "工单标题/简述")
    private String title;

    @Schema(description = "工单类型 (例如 0:计划内, 1:紧急, 2:巡检)", example = "2")
    private Integer orderType;

    @Schema(description = "工单状态 (例如 0:待派发, 1:已派发, 2:已接单, 3:进行中, 4:已完工, 5:已取消, 6:已拒绝)", example = "1")
    private Integer orderStatus;

    @Schema(description = "优先级 (例如 0:低, 1:中, 2:高)")
    private Integer priority;

    @Schema(description = "关联维保协议号")
    private String agreementNo;

    @Schema(description = "客户名称", example = "张三")
    private String customerName;

    @Schema(description = "报修时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] reportTime;

    @Schema(description = "负责维保公司ID", example = "7787")
    private Long maintenanceCompanyId;

    @Schema(description = "指派维修工姓名", example = "芋艿")
    private String assignedToUserName;

    @Schema(description = "设备所属单位ID", example = "15807")
    private Long owningDeptId;

    @Schema(description = "服务片区部门ID", example = "27592")
    private Long serviceAreaDeptId;

    @Schema(description = "故障类型ID", example = "1001")
    private Long issueTypeId;

    @Schema(description = "故障类型名称", example = "硬件故障")
    private String issueTypeName;

}