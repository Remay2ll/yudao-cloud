package cn.iocoder.yudao.module.warranty.controller.admin.component.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 配件新增/修改 Request VO")
@Data
public class WtyComponentSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23476")
    private Long id;

    @Schema(description = "配件编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "配件编号不能为空")
    private String componentCode;

    @Schema(description = "配件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "配件名称不能为空")
    private String componentName;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "配件维修价格")
    private BigDecimal priceMaintain;

    @Schema(description = "配件更换价格")
    private BigDecimal priceChange;

    @Schema(description = "设备状态", example = "1")
    private Boolean status;

}