package cn.iocoder.yudao.module.warranty.dal.mysql.deptaddress;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.deptaddress.DeptAddressDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.deptaddress.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 部门地址 Mapper
 *
 * @author Remay
 */
@Mapper
public interface DeptAddressMapper extends BaseMapperX<DeptAddressDO> {

    default PageResult<DeptAddressDO> selectPage(DeptAddressPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeptAddressDO>()
                .eqIfPresent(DeptAddressDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(DeptAddressDO::getAddressType, reqVO.getAddressType())
                .eqIfPresent(DeptAddressDO::getStreetAddress, reqVO.getStreetAddress())
                .eqIfPresent(DeptAddressDO::getCity, reqVO.getCity())
                .eqIfPresent(DeptAddressDO::getStateProvince, reqVO.getStateProvince())
                .eqIfPresent(DeptAddressDO::getPostalCode, reqVO.getPostalCode())
                .eqIfPresent(DeptAddressDO::getCountry, reqVO.getCountry())
                .eqIfPresent(DeptAddressDO::getIsDefault, reqVO.getIsDefault())
                .betweenIfPresent(DeptAddressDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeptAddressDO::getId));
    }

    default int deleteByDeptId(Long deptId) {
        return delete(new LambdaQueryWrapperX<DeptAddressDO>()
                .eq(DeptAddressDO::getDeptId, deptId));
    }

    void updateNonDefaultByDeptId(@Param("deptId") Long deptId);

    void updateNonDefaultByDeptIdAndExcludeId(@Param("deptId") Long deptId, @Param("excludeId") Long excludeId);

    /**
     * 根据部门ID和是否默认地址查询部门地址
     *
     * @param deptId 部门ID
     * @param isDefault 是否默认地址
     * @return 部门地址列表
     */
    default List<DeptAddressDO> selectByDeptIdAndDefault(Long deptId, Boolean isDefault) {
        return selectList(new LambdaQueryWrapperX<DeptAddressDO>()
                .eq(DeptAddressDO::getDeptId, deptId)
                .eq(DeptAddressDO::getIsDefault, isDefault)
                .orderByDesc(DeptAddressDO::getId));
    }

}