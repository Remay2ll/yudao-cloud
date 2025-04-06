package cn.iocoder.yudao.module.warranty.controller.admin.device;

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

import cn.iocoder.yudao.module.warranty.controller.admin.device.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.device.WtyDeviceDO;
import cn.iocoder.yudao.module.warranty.service.device.WtyDeviceService;

@Tag(name = "管理后台 - 设备")
@RestController
@RequestMapping("/warranty/wty-device")
@Validated
public class WtyDeviceController {

    @Resource
    private WtyDeviceService wtyDeviceService;

    @PostMapping("/create")
    @Operation(summary = "创建设备")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device:create')")
    public CommonResult<Long> createWtyDevice(@Valid @RequestBody WtyDeviceSaveReqVO createReqVO) {
        return success(wtyDeviceService.createWtyDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device:update')")
    public CommonResult<Boolean> updateWtyDevice(@Valid @RequestBody WtyDeviceSaveReqVO updateReqVO) {
        wtyDeviceService.updateWtyDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:wty-device:delete')")
    public CommonResult<Boolean> deleteWtyDevice(@RequestParam("id") Long id) {
        wtyDeviceService.deleteWtyDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device:query')")
    public CommonResult<WtyDeviceRespVO> getWtyDevice(@RequestParam("id") Long id) {
        WtyDeviceDO wtyDevice = wtyDeviceService.getWtyDevice(id);
        return success(BeanUtils.toBean(wtyDevice, WtyDeviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备分页")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device:query')")
    public CommonResult<PageResult<WtyDeviceRespVO>> getWtyDevicePage(@Valid WtyDevicePageReqVO pageReqVO) {
        PageResult<WtyDeviceDO> pageResult = wtyDeviceService.getWtyDevicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WtyDeviceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWtyDeviceExcel(@Valid WtyDevicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WtyDeviceDO> list = wtyDeviceService.getWtyDevicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备.xls", "数据", WtyDeviceRespVO.class,
                        BeanUtils.toBean(list, WtyDeviceRespVO.class));
    }

}