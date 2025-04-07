package cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDO;

@Schema(description = "管理后台 - 设备类型新增/修改 Request VO")
@Data
public class WtyDeviceTypeSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13709")
    private Long id;

    @Schema(description = "类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "类型编码不能为空")
    private String typeCode;

    @Schema(description = "类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "类型名称不能为空")
    private String typeName;

    @Schema(description = "父类型ID", example = "24092")
    private Long parentTypeId;

    @Schema(description = "类型描述", example = "你猜")
    private String description;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    private Integer status;

}