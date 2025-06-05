package cn.iocoder.yudao.module.warranty.dal.mysql.maintenorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorder.MaintenOrderDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorder.vo.*;

/**
 * 维修工单 Mapper
 *
 * @author Remay
 */
@Mapper
public interface MaintenOrderMapper extends BaseMapperX<MaintenOrderDO> {

    default PageResult<MaintenOrderDO> selectPage(MaintenOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintenOrderDO>()
                .betweenIfPresent(MaintenOrderDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(MaintenOrderDO::getWorkOrderNo, reqVO.getWorkOrderNo())
                .eqIfPresent(MaintenOrderDO::getTitle, reqVO.getTitle())
                .eqIfPresent(MaintenOrderDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(MaintenOrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(MaintenOrderDO::getPriority, reqVO.getPriority())
                .eqIfPresent(MaintenOrderDO::getAgreementNo, reqVO.getAgreementNo())
                .likeIfPresent(MaintenOrderDO::getCustomerName, reqVO.getCustomerName())
                .betweenIfPresent(MaintenOrderDO::getReportTime, reqVO.getReportTime())
                .eqIfPresent(MaintenOrderDO::getMaintenanceCompanyId, reqVO.getMaintenanceCompanyId())
                .likeIfPresent(MaintenOrderDO::getAssignedToUserName, reqVO.getAssignedToUserName())
                .eqIfPresent(MaintenOrderDO::getOwningDeptId, reqVO.getOwningDeptId())
                .eqIfPresent(MaintenOrderDO::getServiceAreaDeptId, reqVO.getServiceAreaDeptId())
                .eqIfPresent(MaintenOrderDO::getIssueTypeId, reqVO.getIssueTypeId())
                .likeIfPresent(MaintenOrderDO::getIssueTypeName, reqVO.getIssueTypeName())
                .orderByDesc(MaintenOrderDO::getId));
    }

}