package cn.iocoder.yudao.module.warranty.service.dept;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.dept.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.deptaddress.DeptAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 部门 Service 接口
 *
 * @author Remay
 */
public interface DeptService {

    /**
     * 获得部门
     *
     * @param id 编号
     * @return 部门
     */
    DeptDO getDept(Long id);

    /**
     * 获得部门分页
     *
     * @param pageReqVO 分页查询
     * @return 部门分页
     */
    PageResult<DeptDO> getDeptPage(DeptPageReqVO pageReqVO);

    // ==================== 子表（部门地址） ====================

    /**
     * 获得部门地址分页
     *
     * @param pageReqVO 分页查询
     * @param deptId 部门id
     * @return 部门地址分页
     */
    PageResult<DeptAddressDO> getDeptAddressPage(PageParam pageReqVO, Long deptId);

    /**
     * 创建部门地址
     *
     * @param deptAddress 创建信息
     * @return 编号
     */
    Long createDeptAddress(@Valid DeptAddressDO deptAddress);

    /**
     * 更新部门地址
     *
     * @param deptAddress 更新信息
     */
    void updateDeptAddress(@Valid DeptAddressDO deptAddress);

    /**
     * 删除部门地址
     *
     * @param id 编号
     */
    void deleteDeptAddress(Long id);

	/**
	 * 获得部门地址
	 *
	 * @param id 编号
     * @return 部门地址
	 */
    DeptAddressDO getDeptAddress(Long id);

    /**
     * 根据部门ID和是否默认地址获取网点地址
     *
     * @param deptId 部门ID
     * @param isDefault 是否默认地址
     * @return 部门地址列表
     */
    List<DeptAddressDO> getDeptAddressByDeptIdAndDefault(Long deptId, Boolean isDefault);

}