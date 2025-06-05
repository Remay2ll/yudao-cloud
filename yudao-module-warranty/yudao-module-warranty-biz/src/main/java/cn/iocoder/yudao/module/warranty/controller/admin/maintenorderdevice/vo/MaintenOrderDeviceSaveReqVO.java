package cn.iocoder.yudao.module.warranty.controller.admin.maintenorderdevice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 维修工单设备关联新增/修改 Request VO")
@Data
public class MaintenOrderDeviceSaveReqVO {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "维修工单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1949")
    @NotNull(message = "维修工单ID不能为空")
    private Long workOrderId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24800")
    @NotNull(message = "设备ID不能为空")
    private Long deviceId;

    @Schema(description = "关于此设备在此工单中的备注", example = "你猜")
    private String remark;

}