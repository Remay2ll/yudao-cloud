package cn.iocoder.yudao.module.warranty.controller.admin.issuetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 故障类型树 Response VO")
@Data
public class IssueTypeTreeRespVO {

    @Schema(description = "ID主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "2853")
    private Long id;

    @Schema(description = "故障类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String typeCode;

    @Schema(description = "故障类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
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

    @Schema(description = "是否审核（0：未审核，1：已通过，2：未通过）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isAudit;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "子故障类型列表")
    private List<IssueTypeTreeRespVO> children = new ArrayList<>();

    /**
     * 递归添加子节点
     */
    public void addChild(IssueTypeTreeRespVO child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

} 