package cn.iocoder.yudao.module.warranty.controller.admin.component;

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

import cn.iocoder.yudao.module.warranty.controller.admin.component.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.component.WtyComponentDO;
import cn.iocoder.yudao.module.warranty.service.component.WtyComponentService;

@Tag(name = "管理后台 - 配件")
@RestController
@RequestMapping("/warranty/wty-component")
@Validated
public class WtyComponentController {

    @Resource
    private WtyComponentService wtyComponentService;

    @PostMapping("/create")
    @Operation(summary = "创建配件")
    @PreAuthorize("@ss.hasPermission('warranty:wty-component:create')")
    public CommonResult<Long> createWtyComponent(@Valid @RequestBody WtyComponentSaveReqVO createReqVO) {
        return success(wtyComponentService.createWtyComponent(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新配件")
    @PreAuthorize("@ss.hasPermission('warranty:wty-component:update')")
    public CommonResult<Boolean> updateWtyComponent(@Valid @RequestBody WtyComponentSaveReqVO updateReqVO) {
        wtyComponentService.updateWtyComponent(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除配件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:wty-component:delete')")
    public CommonResult<Boolean> deleteWtyComponent(@RequestParam("id") Long id) {
        wtyComponentService.deleteWtyComponent(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得配件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:wty-component:query')")
    public CommonResult<WtyComponentRespVO> getWtyComponent(@RequestParam("id") Long id) {
        WtyComponentDO wtyComponent = wtyComponentService.getWtyComponent(id);
        return success(BeanUtils.toBean(wtyComponent, WtyComponentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得配件分页")
    @PreAuthorize("@ss.hasPermission('warranty:wty-component:query')")
    public CommonResult<PageResult<WtyComponentRespVO>> getWtyComponentPage(@Valid WtyComponentPageReqVO pageReqVO) {
        PageResult<WtyComponentDO> pageResult = wtyComponentService.getWtyComponentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WtyComponentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出配件 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:wty-component:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWtyComponentExcel(@Valid WtyComponentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WtyComponentDO> list = wtyComponentService.getWtyComponentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "配件.xls", "数据", WtyComponentRespVO.class,
                        BeanUtils.toBean(list, WtyComponentRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得配件列表")
    @PreAuthorize("@ss.hasPermission('warranty:wty-component:query')")
    public CommonResult<List<WtyComponentRespListVO>> getWtyComponentList() {
        List<WtyComponentDO> wtyComponentDOList = wtyComponentService.getWtyComponentList();
        return success(BeanUtils.toBean(wtyComponentDOList, WtyComponentRespListVO.class));
    }
}