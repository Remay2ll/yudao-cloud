package cn.iocoder.yudao.module.warranty.controller.admin.dept.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 部门 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeptRespVO {

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("部门id")
    private Long id;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("部门名称")
    private String name;

    @Schema(description = "父部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "32533")
    @ExcelProperty("父部门id")
    private Long parentId;

    @Schema(description = "负责人", example = "25302")
    @ExcelProperty("负责人")
    private Long leaderUserId;

    @Schema(description = "部门状态（0正常 1停用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "部门状态（0正常 1停用）", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}