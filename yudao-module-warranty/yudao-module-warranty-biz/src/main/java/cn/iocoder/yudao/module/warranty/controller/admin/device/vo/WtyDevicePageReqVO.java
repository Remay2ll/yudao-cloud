package cn.iocoder.yudao.module.warranty.controller.admin.device.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WtyDevicePageReqVO extends PageParam {

    @Schema(description = "设备名称", example = "赵六")
    private String deviceName;

    @Schema(description = "设备编号")
    private String deviceCode;

    @Schema(description = "设备描述", example = "你说的对")
    private String description;

    @Schema(description = "设备类型ID", example = "16119")
    private Long typeId;

    @Schema(description = "所属机构ID", example = "11721")
    private Long deptId;

    @Schema(description = "设备状态（0：停用，1：启用）", example = "1")
    private Boolean status;

    @Schema(description = "是否审核（0：未审核，1：已通过，2：未通过）")
    private Integer isAudit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}