package cn.iocoder.yudao.module.warranty.controller.admin.maintenorderdevice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 维修工单设备关联 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintenOrderDeviceRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5605")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "维修工单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1949")
    @ExcelProperty("维修工单ID")
    private Long workOrderId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24800")
    @ExcelProperty("设备ID")
    private Long deviceId;

}