package cn.iocoder.yudao.module.warranty.controller.admin.maintenorderpart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单配件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintenOrderPartRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15254")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "维修工单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26563")
    @ExcelProperty("维修工单ID")
    private Long workOrderId;

    @Schema(description = "配件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5513")
    @ExcelProperty("配件ID")
    private Long partId;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("数量")
    private Integer quantity;

}