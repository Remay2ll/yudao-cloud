package cn.iocoder.yudao.module.warranty.framework.datapermission.config;

import cn.iocoder.yudao.module.warranty.dal.dataobject.device.WtyDeviceDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorder.MaintenOrderDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.deptaddress.DeptAddressDO;
import cn.iocoder.yudao.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * warranty 模块的数据权限 Configuration
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer warrantyDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // device 基于部门的数据权限
            rule.addDeptColumn(WtyDeviceDO.class, "dept_id"); // WHERE dept_id = ?
            
            // mainten_order 基于部门的数据权限 - 设备所属单位
            rule.addDeptColumn(MaintenOrderDO.class, "owning_dept_id"); // WHERE owning_dept_id = ?
            
            // dept_address 基于部门的数据权限
            rule.addDeptColumn(DeptAddressDO.class, "dept_id"); // WHERE dept_id = ?
            
            // 如果需要基于用户的数据权限，可以添加以下配置：
            // rule.addUserColumn(MaintenOrderDO.class, "reported_by_user_id"); // WHERE reported_by_user_id = ?
            // rule.addUserColumn(MaintenOrderDO.class, "assigned_to_user_id"); // WHERE assigned_to_user_id = ?
        };
    }

} 