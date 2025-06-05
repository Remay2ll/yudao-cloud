package cn.iocoder.yudao.module.warranty.controller.admin.issuetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

@Schema(description = "管理后台 - 故障类型精简树 Response VO")
@Data
public class IssueTypeSimpleTreeRespVO {

    @Schema(description = "ID主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "2853")
    private Long id;

    @Schema(description = "故障类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String typeCode;

    @Schema(description = "故障类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "硬件故障")
    private String typeName;

    @Schema(description = "父级故障类型ID（0表示顶级分类）", example = "8023")
    private Long parentId;

    @Schema(description = "状态（0：停用，1：启用）", example = "1")
    private Boolean status;

    @Schema(description = "子故障类型列表")
    private List<IssueTypeSimpleTreeRespVO> children = new ArrayList<>();

    /**
     * 递归添加子节点
     */
    public void addChild(IssueTypeSimpleTreeRespVO child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

} 