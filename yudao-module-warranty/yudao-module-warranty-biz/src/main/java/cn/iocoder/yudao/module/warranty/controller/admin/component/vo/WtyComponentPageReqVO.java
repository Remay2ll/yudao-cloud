package cn.iocoder.yudao.module.warranty.controller.admin.component.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 配件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WtyComponentPageReqVO extends PageParam {

    @Schema(description = "配件编号")
    private String componentCode;

    @Schema(description = "配件名称", example = "王五")
    private String componentName;

    @Schema(description = "设备状态", example = "1")
    private Boolean status;

    @Schema(description = "是否审核")
    private Integer isAudit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}