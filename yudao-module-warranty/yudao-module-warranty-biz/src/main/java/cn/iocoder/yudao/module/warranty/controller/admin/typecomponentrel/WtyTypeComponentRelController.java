package cn.iocoder.yudao.module.warranty.controller.admin.typecomponentrel;

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

import cn.iocoder.yudao.module.warranty.controller.admin.typecomponentrel.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDO;
import cn.iocoder.yudao.module.warranty.service.typecomponentrel.WtyTypeComponentRelService;

@Tag(name = "管理后台 - 设备类型-配件关联")
@RestController
@RequestMapping("/warranty/wty-type-component-rel")
@Validated
public class WtyTypeComponentRelController {

    @Resource
    private WtyTypeComponentRelService wtyTypeComponentRelService;

    @PostMapping("/create")
    @Operation(summary = "创建设备类型-配件关联")
    @PreAuthorize("@ss.hasPermission('warranty:wty-type-component-rel:create')")
    public CommonResult<Long> createWtyTypeComponentRel(@Valid @RequestBody WtyTypeComponentRelSaveReqVO createReqVO) {
        return success(wtyTypeComponentRelService.createWtyTypeComponentRel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备类型-配件关联")
    @PreAuthorize("@ss.hasPermission('warranty:wty-type-component-rel:update')")
    public CommonResult<Boolean> updateWtyTypeComponentRel(@Valid @RequestBody WtyTypeComponentRelSaveReqVO updateReqVO) {
        wtyTypeComponentRelService.updateWtyTypeComponentRel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备类型-配件关联")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:wty-type-component-rel:delete')")
    public CommonResult<Boolean> deleteWtyTypeComponentRel(@RequestParam("id") Long id) {
        wtyTypeComponentRelService.deleteWtyTypeComponentRel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备类型-配件关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:wty-type-component-rel:query')")
    public CommonResult<WtyTypeComponentRelRespVO> getWtyTypeComponentRel(@RequestParam("id") Long id) {
        WtyTypeComponentRelDO wtyTypeComponentRel = wtyTypeComponentRelService.getWtyTypeComponentRel(id);
        return success(BeanUtils.toBean(wtyTypeComponentRel, WtyTypeComponentRelRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备类型-配件关联分页")
    @PreAuthorize("@ss.hasPermission('warranty:wty-type-component-rel:query')")
    public CommonResult<PageResult<WtyTypeComponentRelRespVO>> getWtyTypeComponentRelPage(@Valid WtyTypeComponentRelPageReqVO pageReqVO) {
        PageResult<WtyTypeComponentRelDO> pageResult = wtyTypeComponentRelService.getWtyTypeComponentRelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WtyTypeComponentRelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备类型-配件关联 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:wty-type-component-rel:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWtyTypeComponentRelExcel(@Valid WtyTypeComponentRelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WtyTypeComponentRelDO> list = wtyTypeComponentRelService.getWtyTypeComponentRelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备类型-配件关联.xls", "数据", WtyTypeComponentRelRespVO.class,
                        BeanUtils.toBean(list, WtyTypeComponentRelRespVO.class));
    }

}