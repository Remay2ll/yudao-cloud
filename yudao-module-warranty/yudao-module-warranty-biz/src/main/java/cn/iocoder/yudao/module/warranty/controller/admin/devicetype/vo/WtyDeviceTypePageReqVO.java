package cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备类型分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WtyDeviceTypePageReqVO extends PageParam {

    @Schema(description = "类型编码")
    private String typeCode;

    @Schema(description = "类型名称", example = "李四")
    private String typeName;

    @Schema(description = "父类型ID", example = "24092")
    private Long parentTypeId;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "是否审核")
    private Integer isAudit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}