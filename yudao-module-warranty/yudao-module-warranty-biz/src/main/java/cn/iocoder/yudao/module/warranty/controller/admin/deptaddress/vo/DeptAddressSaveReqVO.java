package cn.iocoder.yudao.module.warranty.controller.admin.deptaddress.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 部门地址新增/修改 Request VO")
@Data
public class DeptAddressSaveReqVO {

    @Schema(description = "地址编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30942")
    private Long id;

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19798")
    @NotNull(message = "部门id不能为空")
    private Long deptId;

    @Schema(description = "地址类型（例如：办公、仓库）", example = "2")
    private String addressType;

    @Schema(description = "街道地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "街道地址不能为空")
    private String streetAddress;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "省份/州")
    private String stateProvince;

    @Schema(description = "邮政编码")
    private String postalCode;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "是否默认地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否默认地址不能为空")
    private Boolean isDefault;

    @Schema(description = "地区编号", example = "110101")
    private Long areaId;

}