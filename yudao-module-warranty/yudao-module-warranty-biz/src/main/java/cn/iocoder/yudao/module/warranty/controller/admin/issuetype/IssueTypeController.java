package cn.iocoder.yudao.module.warranty.controller.admin.issuetype;

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

import cn.iocoder.yudao.module.warranty.controller.admin.issuetype.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.issuetype.IssueTypeDO;
import cn.iocoder.yudao.module.warranty.service.issuetype.IssueTypeService;

@Tag(name = "管理后台 - 故障类型")
@RestController
@RequestMapping("/warranty/issue-type")
@Validated
public class IssueTypeController {

    @Resource
    private IssueTypeService issueTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建故障类型")
    @PreAuthorize("@ss.hasPermission('warranty:issue-type:create')")
    public CommonResult<Long> createIssueType(@Valid @RequestBody IssueTypeSaveReqVO createReqVO) {
        return success(issueTypeService.createIssueType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新故障类型")
    @PreAuthorize("@ss.hasPermission('warranty:issue-type:update')")
    public CommonResult<Boolean> updateIssueType(@Valid @RequestBody IssueTypeSaveReqVO updateReqVO) {
        issueTypeService.updateIssueType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除故障类型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('warranty:issue-type:delete')")
    public CommonResult<Boolean> deleteIssueType(@RequestParam("id") Long id) {
        issueTypeService.deleteIssueType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得故障类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('warranty:issue-type:query')")
    public CommonResult<IssueTypeRespVO> getIssueType(@RequestParam("id") Long id) {
        IssueTypeDO issueType = issueTypeService.getIssueType(id);
        return success(BeanUtils.toBean(issueType, IssueTypeRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得故障类型列表")
    @PreAuthorize("@ss.hasPermission('warranty:issue-type:query')")
    public CommonResult<List<IssueTypeRespVO>> getIssueTypeList(@Valid IssueTypeListReqVO listReqVO) {
        List<IssueTypeDO> list = issueTypeService.getIssueTypeList(listReqVO);
        return success(BeanUtils.toBean(list, IssueTypeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出故障类型 Excel")
    @PreAuthorize("@ss.hasPermission('warranty:issue-type:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportIssueTypeExcel(@Valid IssueTypeListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<IssueTypeDO> list = issueTypeService.getIssueTypeList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "故障类型.xls", "数据", IssueTypeRespVO.class,
                        BeanUtils.toBean(list, IssueTypeRespVO.class));
    }

    @GetMapping("/simple-tree")
    @Operation(summary = "获得故障类型精简树结构")
    public CommonResult<List<IssueTypeSimpleTreeRespVO>> getIssueTypeSimpleTree() {
        List<IssueTypeSimpleTreeRespVO> list = issueTypeService.getIssueTypeSimpleTree();
        return success(list);
    }

}