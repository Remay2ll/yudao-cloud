package cn.iocoder.yudao.module.warranty.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 设备新增/修改 Request VO")
@Data
public class WtyDeviceSaveReqVO {

    @Schema(description = "ID主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27176")
    private Long id;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "设备名称不能为空")
    private String deviceName;

    @Schema(description = "设备编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备编号不能为空")
    private String deviceCode;

    @Schema(description = "设备描述", example = "你说的对")
    private String description;

    @Schema(description = "设备类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16119")
    @NotNull(message = "设备类型ID不能为空")
    private Long typeId;

    @Schema(description = "所属机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11721")
    @NotNull(message = "所属机构ID不能为空")
    private Long deptId;

    @Schema(description = "设备状态（0：停用，1：启用）", example = "1")
    private Boolean status;

}