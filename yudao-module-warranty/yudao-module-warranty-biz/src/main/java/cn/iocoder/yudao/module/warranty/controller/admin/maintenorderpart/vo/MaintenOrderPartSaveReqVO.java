package cn.iocoder.yudao.module.warranty.controller.admin.maintenorderpart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 工单配件新增/修改 Request VO")
@Data
public class MaintenOrderPartSaveReqVO {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "维修工单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26563")
    @NotNull(message = "维修工单ID不能为空")
    private Long workOrderId;

    @Schema(description = "配件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5513")
    @NotNull(message = "配件ID不能为空")
    private Long partId;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "关于此配件在此工单中的备注", example = "你说的对")
    private String remark;

}