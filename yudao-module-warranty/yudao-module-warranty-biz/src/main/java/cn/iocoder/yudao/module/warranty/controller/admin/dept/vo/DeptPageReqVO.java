package cn.iocoder.yudao.module.warranty.controller.admin.dept.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 部门分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptPageReqVO extends PageParam {

    @Schema(description = "部门id", example = "1024")
    private Long id;

    @Schema(description = "部门名称", example = "李四")
    private String name;

    @Schema(description = "父部门id", example = "32533")
    private Long parentId;

    @Schema(description = "负责人", example = "25302")
    private Long leaderUserId;

    @Schema(description = "部门状态（0正常 1停用）", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}