package cn.iocoder.yudao.module.system.controller.app.tenant;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.tenant.TenantNameRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantDO;
import cn.iocoder.yudao.module.system.service.tenant.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "用户APP - 租户")
@RestController
@RequestMapping("/system/tenant")
public class AppTenantController {

    @Resource
    private TenantService tenantService;

    @GetMapping("/name-list")
    @PermitAll
    @Operation(summary = "获取租户名称列表", description = "不需要认证，获取所有启用状态的租户名称列表，用于登录页面选择租户")
    public CommonResult<List<TenantNameRespVO>> getTenantNameList() {
        List<TenantDO> list = tenantService.getTenantListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(convertList(list, tenantDO -> {
            TenantNameRespVO vo = new TenantNameRespVO();
            vo.setId(tenantDO.getId());
            vo.setName(tenantDO.getName());
            vo.setStatus(tenantDO.getStatus());
            return vo;
        }));
    }

} 