package cn.iocoder.yudao.module.warranty.controller.admin.typecomponentrel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备类型-配件关联 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WtyTypeComponentRelRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13634")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "设备类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13872")
    @ExcelProperty("设备类型ID")
    private Long typeId;

    @Schema(description = "配件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1244")
    @ExcelProperty("配件ID")
    private Long componentId;

    @Schema(description = "配件数量")
    @ExcelProperty("配件数量")
    private Integer quantity;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "配件名称")
    @ExcelProperty("配件名称")
    private String componentName;
}