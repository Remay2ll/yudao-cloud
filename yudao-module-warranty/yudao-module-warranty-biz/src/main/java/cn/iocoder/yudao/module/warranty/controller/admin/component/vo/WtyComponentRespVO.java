package cn.iocoder.yudao.module.warranty.controller.admin.component.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 配件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WtyComponentRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23476")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "配件编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("配件编号")
    private String componentCode;

    @Schema(description = "配件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("配件名称")
    private String componentName;

    @Schema(description = "单位")
    @ExcelProperty(value = "单位", converter = DictConvert.class)
    @DictFormat("warranty_component_unit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String unit;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "配件维修价格")
    @ExcelProperty("配件维修价格")
    private BigDecimal priceMaintain;

    @Schema(description = "配件更换价格")
    @ExcelProperty("配件更换价格")
    private BigDecimal priceChange;

    @Schema(description = "设备状态", example = "1")
    @ExcelProperty(value = "设备状态", converter = DictConvert.class)
    @DictFormat("warranty_component_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean status;

    @Schema(description = "是否审核", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否审核", converter = DictConvert.class)
    @DictFormat("warranty_audit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isAudit;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}