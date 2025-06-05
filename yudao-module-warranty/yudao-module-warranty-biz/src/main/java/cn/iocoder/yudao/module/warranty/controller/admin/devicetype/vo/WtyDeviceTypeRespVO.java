package cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 设备类型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WtyDeviceTypeRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13709")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("类型编码")
    private String typeCode;

    @Schema(description = "类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("类型名称")
    private String typeName;

    @Schema(description = "父类型ID", example = "24092")
    @ExcelProperty("父类型ID")
    private Long parentTypeId;

    @Schema(description = "是否叶子节点")
    @ExcelProperty("是否叶子节点（0：否，1：是）")
    private Integer isLeaf;

    @Schema(description = "类型描述", example = "你猜")
    @ExcelProperty("类型描述")
    private String description;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("warranty_device_type_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "是否审核", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否审核", converter = DictConvert.class)
    @DictFormat("warranty_audit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isAudit;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}