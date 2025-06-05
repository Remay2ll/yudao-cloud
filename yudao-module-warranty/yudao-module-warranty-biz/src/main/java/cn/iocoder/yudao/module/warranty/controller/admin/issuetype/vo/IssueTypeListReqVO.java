package cn.iocoder.yudao.module.warranty.controller.admin.issuetype.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 故障类型列表 Request VO")
@Data
public class IssueTypeListReqVO {

    @Schema(description = "故障类型编码")
    private String typeCode;

    @Schema(description = "故障类型名称", example = "王五")
    private String typeName;

    @Schema(description = "默认优先级（1-高，2-中，3-低）")
    private Integer priorityLevel;

    @Schema(description = "状态（0：停用，1：启用）", example = "2")
    private Boolean status;

    @Schema(description = "是否审核（0：未审核，1：已通过，2：未通过）")
    private Integer isAudit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}