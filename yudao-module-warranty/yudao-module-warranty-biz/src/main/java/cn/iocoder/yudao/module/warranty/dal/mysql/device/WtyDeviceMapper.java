package cn.iocoder.yudao.module.warranty.dal.mysql.device;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.device.WtyDeviceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.device.vo.*;

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

}