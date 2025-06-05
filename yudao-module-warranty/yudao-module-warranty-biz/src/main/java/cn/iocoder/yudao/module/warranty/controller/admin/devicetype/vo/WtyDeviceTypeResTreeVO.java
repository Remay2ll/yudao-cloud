package cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 设备类型 Response VO - 树形控件")
@Data
@ExcelIgnoreUnannotated
public class WtyDeviceTypeResTreeVO {

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
}