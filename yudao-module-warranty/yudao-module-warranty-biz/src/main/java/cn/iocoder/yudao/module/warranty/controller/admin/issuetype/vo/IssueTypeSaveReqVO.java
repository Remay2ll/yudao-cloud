package cn.iocoder.yudao.module.warranty.controller.admin.issuetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 故障类型新增/修改 Request VO")
@Data
public class IssueTypeSaveReqVO {

    @Schema(description = "ID主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "2853")
    private Long id;

    @Schema(description = "故障类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "故障类型编码不能为空")
    private String typeCode;

    @Schema(description = "故障类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "故障类型名称不能为空")
    private String typeName;

    @Schema(description = "故障类型描述", example = "随便")
    private String description;

    @Schema(description = "父级故障类型ID（0表示顶级分类）", example = "8023")
    private Long parentId;

    @Schema(description = "默认优先级（1-高，2-中，3-低）")
    private Integer priorityLevel;

    @Schema(description = "预计处理时长（分钟）")
    private Integer expectedDuration;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "状态（0：停用，1：启用）", example = "2")
    private Boolean status;

}