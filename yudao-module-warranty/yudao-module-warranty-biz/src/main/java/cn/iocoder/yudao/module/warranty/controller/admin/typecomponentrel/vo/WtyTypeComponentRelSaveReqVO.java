package cn.iocoder.yudao.module.warranty.controller.admin.typecomponentrel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 设备类型-配件关联新增/修改 Request VO")
@Data
public class WtyTypeComponentRelSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13634")
    private Long id;

    @Schema(description = "设备类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13872")
    @NotNull(message = "设备类型ID不能为空")
    private Long typeId;

    @Schema(description = "配件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1244")
    @NotNull(message = "配件ID不能为空")
    private Long componentId;

    @Schema(description = "配件数量")
    private Integer quantity;

}