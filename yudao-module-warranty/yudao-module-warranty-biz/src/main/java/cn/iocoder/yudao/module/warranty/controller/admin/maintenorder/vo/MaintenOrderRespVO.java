package cn.iocoder.yudao.module.warranty.controller.admin.maintenorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 维修工单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintenOrderRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25989")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "工单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("工单编号")
    private String workOrderNo;

    @Schema(description = "工单标题/简述")
    @ExcelProperty("工单标题/简述")
    private String title;

    @Schema(description = "工单类型 (例如 0:计划内, 1:紧急, 2:巡检)", example = "2")
    @ExcelProperty(value = "工单类型 (例如 0:计划内, 1:紧急, 2:巡检)", converter = DictConvert.class)
    @DictFormat("warranty_order_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer orderType;

    @Schema(description = "工单状态 (例如 0:待派发, 1:已派发, 2:已接单, 3:进行中, 4:已完工, 5:已取消, 6:已拒绝)", example = "1")
    @ExcelProperty(value = "工单状态 (例如 0:待派发, 1:已派发, 2:已接单, 3:进行中, 4:已完工, 5:已取消, 6:已拒绝)", converter = DictConvert.class)
    @DictFormat("warranty_order_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer orderStatus;

    @Schema(description = "优先级 (例如 0:低, 1:中, 2:高)")
    @ExcelProperty(value = "优先级 (例如 0:低, 1:中, 2:高)", converter = DictConvert.class)
    @DictFormat("warranty_order_priority") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer priority;

    @Schema(description = "关联维保协议号")
    @ExcelProperty("关联维保协议号")
    private String agreementNo;

    @Schema(description = "客户名称", example = "张三")
    @ExcelProperty("客户名称")
    private String customerName;

    @Schema(description = "客户联系人", example = "王五")
    @ExcelProperty("客户联系人")
    private String customerContactName;

    @Schema(description = "客户联系电话")
    @ExcelProperty("客户联系电话")
    private String customerContactPhone;

    @Schema(description = "客户地址/报修地址")
    @ExcelProperty("客户地址/报修地址")
    private String customerAddress;

    @Schema(description = "报修时间")
    @ExcelProperty("报修时间")
    private LocalDateTime reportTime;

    @Schema(description = "设备所属单位ID", example = "15807")
    @ExcelProperty("设备所属单位ID")
    private Long owningDeptId;

    @Schema(description = "服务片区部门ID", example = "27592")
    @ExcelProperty("服务片区部门ID")
    private Long serviceAreaDeptId;

    @Schema(description = "故障类型ID", example = "1001")
    @ExcelProperty("故障类型ID")
    private Long issueTypeId;

    @Schema(description = "故障类型名称", example = "硬件故障")
    @ExcelProperty("故障类型名称")
    private String issueTypeName;

    @Schema(description = "预计处理时长(分钟)", example = "120")
    @ExcelProperty("预计处理时长(分钟)")
    private Integer estimatedDuration;

}