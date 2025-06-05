package cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备类型树 Request VO")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class WtyDeviceTypeTreeReqVO{
    @Schema(description = "父类型ID")
    private List<Long> parentTypeIds;

}