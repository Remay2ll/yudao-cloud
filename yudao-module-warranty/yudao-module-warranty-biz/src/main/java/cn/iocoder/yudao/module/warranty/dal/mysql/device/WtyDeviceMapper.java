package cn.iocoder.yudao.module.warranty.dal.mysql.device;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.device.WtyDeviceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.device.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 设备 Mapper
 *
 * @author Remay
 */
@Mapper
public interface WtyDeviceMapper extends BaseMapperX<WtyDeviceDO> {

    default PageResult<WtyDeviceDO> selectPage(WtyDevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WtyDeviceDO>()
                .likeIfPresent(WtyDeviceDO::getDeviceName, reqVO.getDeviceName())
                .eqIfPresent(WtyDeviceDO::getDeviceCode, reqVO.getDeviceCode())
                .likeIfPresent(WtyDeviceDO::getDescription, reqVO.getDescription())
                .eqIfPresent(WtyDeviceDO::getTypeId, reqVO.getTypeId())
                .eqIfPresent(WtyDeviceDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(WtyDeviceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WtyDeviceDO::getIsAudit, reqVO.getIsAudit())
                .betweenIfPresent(WtyDeviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WtyDeviceDO::getId));
    }

    // WtyDeviceDO selectByDeviceCode(@Param("deviceCode") String deviceCode); // 已被 BaseMapperX.selectOne 替代

    default List<WtyDeviceDO> selectListByDeptIdsAndStatus(Collection<Long> deptIds, Integer status) {
        return selectList(new LambdaQueryWrapperX<WtyDeviceDO>()
                .in(WtyDeviceDO::getDeptId, deptIds) // 使用 in 而不是 inIfPresent，因为 deptIds 是必须的
                .eq(WtyDeviceDO::getStatus, status)   // status 也是必须的
                .orderByDesc(WtyDeviceDO::getId)); // 或其他排序规则
    }

    WtyDeviceDO selectByIdWithDetails(@Param("id") Long id);

    WtyDeviceDO selectOneByDeviceCodeWithDetails(@Param("deviceCode") String deviceCode);

}