package cn.iocoder.yudao.module.warranty.dal.mysql.maintenorderdevice;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice.MaintenOrderDeviceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorderdevice.vo.*;

/**
 * 维修工单设备关联 Mapper
 *
 * @author Remay
 */
@Mapper
public interface MaintenOrderDeviceMapper extends BaseMapperX<MaintenOrderDeviceDO> {

    default PageResult<MaintenOrderDeviceDO> selectPage(MaintenOrderDevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintenOrderDeviceDO>()
                .betweenIfPresent(MaintenOrderDeviceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MaintenOrderDeviceDO::getWorkOrderId, reqVO.getWorkOrderId())
                .eqIfPresent(MaintenOrderDeviceDO::getDeviceId, reqVO.getDeviceId())
                .orderByDesc(MaintenOrderDeviceDO::getId));
    }

    default List<MaintenOrderDeviceDO> selectListByWorkOrderId(Long workOrderId) {
        return selectList(MaintenOrderDeviceDO::getWorkOrderId, workOrderId);
    }

    default void deleteByWorkOrderId(Long workOrderId) {
        delete(new LambdaQueryWrapperX<MaintenOrderDeviceDO>()
                .eq(MaintenOrderDeviceDO::getWorkOrderId, workOrderId));
    }

}