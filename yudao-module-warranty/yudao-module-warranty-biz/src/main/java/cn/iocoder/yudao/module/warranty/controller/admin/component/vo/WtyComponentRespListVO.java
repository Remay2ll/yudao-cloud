package cn.iocoder.yudao.module.warranty.controller.admin.component.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 配件 Response VO - List")
@Data
@ExcelIgnoreUnannotated
public class WtyComponentRespListVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23476")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "配件编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("配件编号")
    private String componentCode;

    @Schema(description = "配件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("配件名称")
    private String componentName;

}