package cn.iocoder.yudao.module.warranty.controller.admin.dept;

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

import cn.iocoder.yudao.module.warranty.controller.admin.dept.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.deptaddress.DeptAddressDO;
import cn.iocoder.yudao.module.warranty.service.dept.DeptService;

@Tag(name = "管理后台 - 部门")
@RestController
@RequestMapping("/warranty/dept")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @GetMapping("/get")
    @Operation(summary = "获得部门")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:dept:query')")
    public CommonResult<DeptRespVO> getDept(@RequestParam("id") Long id) {
        DeptDO dept = deptService.getDept(id);
        return success(BeanUtils.toBean(dept, DeptRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得部门分页")
    @PreAuthorize("@ss.hasPermission('warranty:dept:query')")
    public CommonResult<PageResult<DeptRespVO>> getDeptPage(@Valid DeptPageReqVO pageReqVO) {
        PageResult<DeptDO> pageResult = deptService.getDeptPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeptRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出部门 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:dept:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDeptExcel(@Valid DeptPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeptDO> list = deptService.getDeptPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "部门.xls", "数据", DeptRespVO.class,
                        BeanUtils.toBean(list, DeptRespVO.class));
    }

    // ==================== 子表（部门地址） ====================

    @GetMapping("/dept-address/page")
    @Operation(summary = "获得部门地址分页")
    @Parameter(name = "deptId", description = "部门id")
    @PreAuthorize("@ss.hasPermission('warranty:dept:query')")
    public CommonResult<PageResult<DeptAddressDO>> getDeptAddressPage(PageParam pageReqVO,
                                                                                        @RequestParam("deptId") Long deptId) {
        return success(deptService.getDeptAddressPage(pageReqVO, deptId));
    }

    @PostMapping("/dept-address/create")
    @Operation(summary = "创建部门地址")
    @PreAuthorize("@ss.hasPermission('warranty:dept:create')")
    public CommonResult<Long> createDeptAddress(@Valid @RequestBody DeptAddressDO deptAddress) {
        return success(deptService.createDeptAddress(deptAddress));
    }

    @PutMapping("/dept-address/update")
    @Operation(summary = "更新部门地址")
    @PreAuthorize("@ss.hasPermission('warranty:dept:update')")
    public CommonResult<Boolean> updateDeptAddress(@Valid @RequestBody DeptAddressDO deptAddress) {
        deptService.updateDeptAddress(deptAddress);
        return success(true);
    }

    @DeleteMapping("/dept-address/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除部门地址")
    @PreAuthorize("@ss.hasPermission('warranty:dept:delete')")
    public CommonResult<Boolean> deleteDeptAddress(@RequestParam("id") Long id) {
        deptService.deleteDeptAddress(id);
        return success(true);
    }

	@GetMapping("/dept-address/get")
	@Operation(summary = "获得部门地址")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:dept:query')")
	public CommonResult<DeptAddressDO> getDeptAddress(@RequestParam("id") Long id) {
	    return success(deptService.getDeptAddress(id));
	}

    @GetMapping("/dept-address/get-by-dept-and-default")
    @Operation(summary = "根据部门ID和是否默认地址获取网点地址")
    @Parameter(name = "deptId", description = "部门ID", required = true)
    @Parameter(name = "isDefault", description = "是否默认地址", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:dept:query')")
    public CommonResult<List<DeptAddressDO>> getDeptAddressByDeptIdAndDefault(
            @RequestParam("deptId") Long deptId,
            @RequestParam("isDefault") Boolean isDefault) {
        List<DeptAddressDO> addresses = deptService.getDeptAddressByDeptIdAndDefault(deptId, isDefault);
        return success(addresses);
    }

    @GetMapping("/dept-address/get-default")
    @Operation(summary = "获取网点的默认地址")
    @Parameter(name = "deptId", description = "部门ID", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:dept:query')")
    public CommonResult<DeptAddressDO> getDefaultDeptAddress(@RequestParam("deptId") Long deptId) {
        List<DeptAddressDO> addresses = deptService.getDeptAddressByDeptIdAndDefault(deptId, true);
        // 返回第一个默认地址，通常一个部门只有一个默认地址
        DeptAddressDO defaultAddress = addresses.isEmpty() ? null : addresses.get(0);
        return success(defaultAddress);
    }

}