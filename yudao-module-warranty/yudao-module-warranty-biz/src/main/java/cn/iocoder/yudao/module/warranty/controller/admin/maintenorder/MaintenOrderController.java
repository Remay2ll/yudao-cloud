package cn.iocoder.yudao.module.warranty.controller.admin.maintenorder;

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

import cn.iocoder.yudao.module.warranty.controller.admin.maintenorder.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorder.MaintenOrderDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice.MaintenOrderDeviceDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderpart.MaintenOrderPartDO;
import cn.iocoder.yudao.module.warranty.service.maintenorder.MaintenOrderService;

@Tag(name = "管理后台 - 维修工单")
@RestController
@RequestMapping("/warranty/mainten-order")
@Validated
public class MaintenOrderController {

    @Resource
    private MaintenOrderService maintenOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建维修工单")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order:create')")
    public CommonResult<Long> createMaintenOrder(@Valid @RequestBody MaintenOrderSaveReqVO createReqVO) {
        return success(maintenOrderService.createMaintenOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新维修工单")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order:update')")
    public CommonResult<Boolean> updateMaintenOrder(@Valid @RequestBody MaintenOrderSaveReqVO updateReqVO) {
        maintenOrderService.updateMaintenOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除维修工单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order:delete')")
    public CommonResult<Boolean> deleteMaintenOrder(@RequestParam("id") Long id) {
        maintenOrderService.deleteMaintenOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得维修工单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order:query')")
    public CommonResult<MaintenOrderRespVO> getMaintenOrder(@RequestParam("id") Long id) {
        MaintenOrderDO maintenOrder = maintenOrderService.getMaintenOrder(id);
        return success(BeanUtils.toBean(maintenOrder, MaintenOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得维修工单分页")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order:query')")
    public CommonResult<PageResult<MaintenOrderRespVO>> getMaintenOrderPage(@Valid MaintenOrderPageReqVO pageReqVO) {
        PageResult<MaintenOrderDO> pageResult = maintenOrderService.getMaintenOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MaintenOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出维修工单 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintenOrderExcel(@Valid MaintenOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintenOrderDO> list = maintenOrderService.getMaintenOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "维修工单.xls", "数据", MaintenOrderRespVO.class,
                        BeanUtils.toBean(list, MaintenOrderRespVO.class));
    }

    // ==================== 子表（维修工单设备关联） ====================

    @GetMapping("/mainten-order-device/list-by-work-order-id")
    @Operation(summary = "获得维修工单设备关联列表")
    @Parameter(name = "workOrderId", description = "维修工单ID")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order:query')")
    public CommonResult<List<MaintenOrderDeviceDO>> getMaintenOrderDeviceListByWorkOrderId(@RequestParam("workOrderId") Long workOrderId) {
        return success(maintenOrderService.getMaintenOrderDeviceListByWorkOrderId(workOrderId));
    }

    // ==================== 子表（工单配件） ====================

    @GetMapping("/mainten-order-part/list-by-work-order-id")
    @Operation(summary = "获得工单配件列表")
    @Parameter(name = "workOrderId", description = "维修工单ID")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order:query')")
    public CommonResult<List<MaintenOrderPartDO>> getMaintenOrderPartListByWorkOrderId(@RequestParam("workOrderId") Long workOrderId) {
        return success(maintenOrderService.getMaintenOrderPartListByWorkOrderId(workOrderId));
    }

}