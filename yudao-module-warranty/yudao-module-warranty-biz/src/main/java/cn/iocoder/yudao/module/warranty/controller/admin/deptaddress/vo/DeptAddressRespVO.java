package cn.iocoder.yudao.module.warranty.controller.admin.deptaddress.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 部门地址 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeptAddressRespVO {

    @Schema(description = "地址编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30942")
    @ExcelProperty("地址编号")
    private Long id;

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19798")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "地址类型（例如：办公、仓库）", example = "2")
    @ExcelProperty("地址类型（例如：办公、仓库）")
    private String addressType;

    @Schema(description = "街道地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("街道地址")
    private String streetAddress;

    @Schema(description = "城市")
    @ExcelProperty("城市")
    private String city;

    @Schema(description = "省份/州")
    @ExcelProperty("省份/州")
    private String stateProvince;

    @Schema(description = "邮政编码")
    @ExcelProperty("邮政编码")
    private String postalCode;

    @Schema(description = "国家")
    @ExcelProperty("国家")
    private String country;

    @Schema(description = "是否默认地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否默认地址")
    private Boolean isDefault;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}