package cn.iocoder.yudao.module.warranty.controller.admin.deptaddress.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 部门地址分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptAddressPageReqVO extends PageParam {

    @Schema(description = "部门id", example = "19798")
    private Long deptId;

    @Schema(description = "地址类型（例如：办公、仓库）", example = "2")
    private String addressType;

    @Schema(description = "街道地址")
    private String streetAddress;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "省份/州")
    private String stateProvince;

    @Schema(description = "邮政编码")
    private String postalCode;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "是否默认地址")
    private Boolean isDefault;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}