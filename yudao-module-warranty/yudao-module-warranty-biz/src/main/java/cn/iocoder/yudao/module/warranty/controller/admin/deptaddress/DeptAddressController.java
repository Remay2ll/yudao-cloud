package cn.iocoder.yudao.module.warranty.controller.admin.deptaddress;

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

import cn.iocoder.yudao.module.warranty.controller.admin.deptaddress.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.deptaddress.DeptAddressDO;
import cn.iocoder.yudao.module.warranty.service.deptaddress.DeptAddressService;

@Tag(name = "管理后台 - 部门地址")
@RestController
@RequestMapping("/warranty/dept-address")
@Validated
public class DeptAddressController {

    @Resource
    private DeptAddressService deptAddressService;

    @PostMapping("/create")
    @Operation(summary = "创建部门地址")
    @PreAuthorize("@ss.hasPermission('warranty:dept-address:create')")
    public CommonResult<Long> createDeptAddress(@Valid @RequestBody DeptAddressSaveReqVO createReqVO) {
        return success(deptAddressService.createDeptAddress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新部门地址")
    @PreAuthorize("@ss.hasPermission('warranty:dept-address:update')")
    public CommonResult<Boolean> updateDeptAddress(@Valid @RequestBody DeptAddressSaveReqVO updateReqVO) {
        deptAddressService.updateDeptAddress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除部门地址")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:dept-address:delete')")
    public CommonResult<Boolean> deleteDeptAddress(@RequestParam("id") Long id) {
        deptAddressService.deleteDeptAddress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得部门地址")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:dept-address:query')")
    public CommonResult<DeptAddressRespVO> getDeptAddress(@RequestParam("id") Long id) {
        DeptAddressDO deptAddress = deptAddressService.getDeptAddress(id);
        return success(BeanUtils.toBean(deptAddress, DeptAddressRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得部门地址分页")
    @PreAuthorize("@ss.hasPermission('warranty:dept-address:query')")
    public CommonResult<PageResult<DeptAddressRespVO>> getDeptAddressPage(@Valid DeptAddressPageReqVO pageReqVO) {
        PageResult<DeptAddressDO> pageResult = deptAddressService.getDeptAddressPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeptAddressRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出部门地址 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:dept-address:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDeptAddressExcel(@Valid DeptAddressPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeptAddressDO> list = deptAddressService.getDeptAddressPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "部门地址.xls", "数据", DeptAddressRespVO.class,
                        BeanUtils.toBean(list, DeptAddressRespVO.class));
    }

}