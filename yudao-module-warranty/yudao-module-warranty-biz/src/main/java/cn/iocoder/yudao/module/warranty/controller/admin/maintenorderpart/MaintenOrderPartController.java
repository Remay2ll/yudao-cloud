package cn.iocoder.yudao.module.warranty.controller.admin.maintenorderpart;

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

import cn.iocoder.yudao.module.warranty.controller.admin.maintenorderpart.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderpart.MaintenOrderPartDO;
import cn.iocoder.yudao.module.warranty.service.maintenorderpart.MaintenOrderPartService;

@Tag(name = "管理后台 - 工单配件")
@RestController
@RequestMapping("/warranty/mainten-order-part")
@Validated
public class MaintenOrderPartController {

    @Resource
    private MaintenOrderPartService maintenOrderPartService;

    @PostMapping("/create")
    @Operation(summary = "创建工单配件")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-part:create')")
    public CommonResult<Long> createMaintenOrderPart(@Valid @RequestBody MaintenOrderPartSaveReqVO createReqVO) {
        return success(maintenOrderPartService.createMaintenOrderPart(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工单配件")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-part:update')")
    public CommonResult<Boolean> updateMaintenOrderPart(@Valid @RequestBody MaintenOrderPartSaveReqVO updateReqVO) {
        maintenOrderPartService.updateMaintenOrderPart(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工单配件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-part:delete')")
    public CommonResult<Boolean> deleteMaintenOrderPart(@RequestParam("id") Long id) {
        maintenOrderPartService.deleteMaintenOrderPart(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得工单配件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-part:query')")
    public CommonResult<MaintenOrderPartRespVO> getMaintenOrderPart(@RequestParam("id") Long id) {
        MaintenOrderPartDO maintenOrderPart = maintenOrderPartService.getMaintenOrderPart(id);
        return success(BeanUtils.toBean(maintenOrderPart, MaintenOrderPartRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工单配件分页")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-part:query')")
    public CommonResult<PageResult<MaintenOrderPartRespVO>> getMaintenOrderPartPage(@Valid MaintenOrderPartPageReqVO pageReqVO) {
        PageResult<MaintenOrderPartDO> pageResult = maintenOrderPartService.getMaintenOrderPartPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MaintenOrderPartRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工单配件 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:mainten-order-part:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintenOrderPartExcel(@Valid MaintenOrderPartPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintenOrderPartDO> list = maintenOrderPartService.getMaintenOrderPartPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "工单配件.xls", "数据", MaintenOrderPartRespVO.class,
                        BeanUtils.toBean(list, MaintenOrderPartRespVO.class));
    }

}