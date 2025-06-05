package cn.iocoder.yudao.module.warranty.dal.mysql.maintenorderpart;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderpart.MaintenOrderPartDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorderpart.vo.*;

/**
 * 工单配件 Mapper
 *
 * @author Remay
 */
@Mapper
public interface MaintenOrderPartMapper extends BaseMapperX<MaintenOrderPartDO> {

    default PageResult<MaintenOrderPartDO> selectPage(MaintenOrderPartPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintenOrderPartDO>()
                .betweenIfPresent(MaintenOrderPartDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MaintenOrderPartDO::getWorkOrderId, reqVO.getWorkOrderId())
                .eqIfPresent(MaintenOrderPartDO::getPartId, reqVO.getPartId())
                .orderByDesc(MaintenOrderPartDO::getId));
    }

    default List<MaintenOrderPartDO> selectListByWorkOrderId(Long workOrderId) {
        return selectList(MaintenOrderPartDO::getWorkOrderId, workOrderId);
    }

    default void deleteByWorkOrderId(Long workOrderId) {
        delete(new LambdaQueryWrapperX<MaintenOrderPartDO>()
                .eq(MaintenOrderPartDO::getWorkOrderId, workOrderId));
    }

}