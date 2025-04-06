package cn.iocoder.yudao.module.warranty.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 设备 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WtyDeviceRespVO {

    @Schema(description = "ID主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27176")
    @ExcelProperty("ID主键")
    private Long id;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("设备名称")
    private String deviceName;

    @Schema(description = "设备编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备编号")
    private String deviceCode;

    @Schema(description = "设备描述", example = "你说的对")
    @ExcelProperty("设备描述")
    private String description;

    @Schema(description = "设备类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16119")
    @ExcelProperty("设备类型ID")
    private Long typeId;

    @Schema(description = "所属机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11721")
    @ExcelProperty("所属机构ID")
    private Long deptId;

    @Schema(description = "设备状态（0：停用，1：启用）", example = "1")
    @ExcelProperty(value = "设备状态（0：停用，1：启用）", converter = DictConvert.class)
    @DictFormat("warranty_device_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean status;

    @Schema(description = "是否审核（0：未审核，1：已通过，2：未通过）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否审核（0：未审核，1：已通过，2：未通过）", converter = DictConvert.class)
    @DictFormat("warranty_audit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isAudit;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}