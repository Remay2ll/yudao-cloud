package cn.iocoder.yudao.module.warranty.controller.admin.devicetype;

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

import cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype.WtyDeviceTypeDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDO;
import cn.iocoder.yudao.module.warranty.service.devicetype.WtyDeviceTypeService;

@Tag(name = "管理后台 - 设备类型")
@RestController
@RequestMapping("/warranty/wty-device-type")
@Validated
public class WtyDeviceTypeController {

    @Resource
    private WtyDeviceTypeService wtyDeviceTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建设备类型")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:create')")
    public CommonResult<Long> createWtyDeviceType(@Valid @RequestBody WtyDeviceTypeSaveReqVO createReqVO) {
        return success(wtyDeviceTypeService.createWtyDeviceType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备类型")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:update')")
    public CommonResult<Boolean> updateWtyDeviceType(@Valid @RequestBody WtyDeviceTypeSaveReqVO updateReqVO) {
        wtyDeviceTypeService.updateWtyDeviceType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备类型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:delete')")
    public CommonResult<Boolean> deleteWtyDeviceType(@RequestParam("id") Long id) {
        wtyDeviceTypeService.deleteWtyDeviceType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:query')")
    public CommonResult<WtyDeviceTypeRespVO> getWtyDeviceType(@RequestParam("id") Long id) {
        WtyDeviceTypeDO wtyDeviceType = wtyDeviceTypeService.getWtyDeviceType(id);
        return success(BeanUtils.toBean(wtyDeviceType, WtyDeviceTypeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备类型分页")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:query')")
    public CommonResult<PageResult<WtyDeviceTypeRespVO>> getWtyDeviceTypePage(@Valid WtyDeviceTypePageReqVO pageReqVO) {
        PageResult<WtyDeviceTypeDO> pageResult = wtyDeviceTypeService.getWtyDeviceTypePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WtyDeviceTypeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备类型 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWtyDeviceTypeExcel(@Valid WtyDeviceTypePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WtyDeviceTypeDO> list = wtyDeviceTypeService.getWtyDeviceTypePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备类型.xls", "数据", WtyDeviceTypeRespVO.class,
                        BeanUtils.toBean(list, WtyDeviceTypeRespVO.class));
    }

    // ==================== 子表（设备类型-配件关联） ====================

    @GetMapping("/wty-type-component-rel/page")
    @Operation(summary = "获得设备类型-配件关联分页")
    @Parameter(name = "typeId", description = "设备类型ID")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:query')")
    public CommonResult<PageResult<WtyTypeComponentRelDO>> getWtyTypeComponentRelPage(PageParam pageReqVO,
                                                                                        @RequestParam("typeId") Long typeId) {
        return success(wtyDeviceTypeService.getWtyTypeComponentRelPage(pageReqVO, typeId));
    }

    @PostMapping("/wty-type-component-rel/create")
    @Operation(summary = "创建设备类型-配件关联")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:create')")
    public CommonResult<Long> createWtyTypeComponentRel(@Valid @RequestBody WtyTypeComponentRelDO wtyTypeComponentRel) {
        return success(wtyDeviceTypeService.createWtyTypeComponentRel(wtyTypeComponentRel));
    }

    @PutMapping("/wty-type-component-rel/update")
    @Operation(summary = "更新设备类型-配件关联")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:update')")
    public CommonResult<Boolean> updateWtyTypeComponentRel(@Valid @RequestBody WtyTypeComponentRelDO wtyTypeComponentRel) {
        wtyDeviceTypeService.updateWtyTypeComponentRel(wtyTypeComponentRel);
        return success(true);
    }

    @DeleteMapping("/wty-type-component-rel/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除设备类型-配件关联")
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:delete')")
    public CommonResult<Boolean> deleteWtyTypeComponentRel(@RequestParam("id") Long id) {
        wtyDeviceTypeService.deleteWtyTypeComponentRel(id);
        return success(true);
    }

	@GetMapping("/wty-type-component-rel/get")
	@Operation(summary = "获得设备类型-配件关联")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:wty-device-type:query')")
	public CommonResult<WtyTypeComponentRelDO> getWtyTypeComponentRel(@RequestParam("id") Long id) {
	    return success(wtyDeviceTypeService.getWtyTypeComponentRel(id));
	}

}