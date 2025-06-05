package cn.iocoder.yudao.module.warranty.service.deptaddress;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.deptaddress.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.deptaddress.DeptAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 部门地址 Service 接口
 *
 * @author Remay
 */
public interface DeptAddressService {

    /**
     * 创建部门地址
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeptAddress(@Valid DeptAddressSaveReqVO createReqVO);

    /**
     * 更新部门地址
     *
     * @param updateReqVO 更新信息
     */
    void updateDeptAddress(@Valid DeptAddressSaveReqVO updateReqVO);

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
     * 获得部门地址分页
     *
     * @param pageReqVO 分页查询
     * @return 部门地址分页
     */
    PageResult<DeptAddressDO> getDeptAddressPage(DeptAddressPageReqVO pageReqVO);

}