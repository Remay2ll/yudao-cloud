package cn.iocoder.yudao.module.warranty.controller.admin.maintenorderdevice;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.warranty.controller.admin.maintenorderdevice.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice.MaintenOrderDeviceDO;
import cn.iocoder.yudao.module.warranty.service.maintenorderdevice.MaintenOrderDeviceService;

@Tag(name = "管理后台 - 维修工单设备关联")
@RestController
@RequestMapping("/warranty/mainten-order-device")
@Validated
public class MaintenOrderDeviceController {

    @Resource
    private MaintenOrderDeviceService maintenOrderDeviceService;

    @PostMapping("/create")
    @Operation(summary = "创建维修工单设备关联")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-device:create')")
    public CommonResult<Long> createMaintenOrderDevice(@Valid @RequestBody MaintenOrderDeviceSaveReqVO createReqVO) {
        return success(maintenOrderDeviceService.createMaintenOrderDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新维修工单设备关联")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-device:update')")
    public CommonResult<Boolean> updateMaintenOrderDevice(@Valid @RequestBody MaintenOrderDeviceSaveReqVO updateReqVO) {
        maintenOrderDeviceService.updateMaintenOrderDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除维修工单设备关联")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-device:delete')")
    public CommonResult<Boolean> deleteMaintenOrderDevice(@RequestParam("id") Long id) {
        maintenOrderDeviceService.deleteMaintenOrderDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得维修工单设备关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-device:query')")
    public CommonResult<MaintenOrderDeviceRespVO> getMaintenOrderDevice(@RequestParam("id") Long id) {
        MaintenOrderDeviceDO maintenOrderDevice = maintenOrderDeviceService.getMaintenOrderDevice(id);
        return success(BeanUtils.toBean(maintenOrderDevice, MaintenOrderDeviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得维修工单设备关联分页")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-device:query')")
    public CommonResult<PageResult<MaintenOrderDeviceRespVO>> getMaintenOrderDevicePage(@Valid MaintenOrderDevicePageReqVO pageReqVO) {
        PageResult<MaintenOrderDeviceDO> pageResult = maintenOrderDeviceService.getMaintenOrderDevicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MaintenOrderDeviceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出维修工单设备关联 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-device:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintenOrderDeviceExcel(@Valid MaintenOrderDevicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintenOrderDeviceDO> list = maintenOrderDeviceService.getMaintenOrderDevicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "维修工单设备关联.xls", "数据", MaintenOrderDeviceRespVO.class,
                        BeanUtils.toBean(list, MaintenOrderDeviceRespVO.class));
    }

}