package cn.iocoder.yudao.module.warranty.service.maintenorder;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorder.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorder.MaintenOrderDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice.MaintenOrderDeviceDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderpart.MaintenOrderPartDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 维修工单 Service 接口
 *
 * @author Remay
 */
public interface MaintenOrderService {

    /**
     * 创建维修工单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaintenOrder(@Valid MaintenOrderSaveReqVO createReqVO);

    /**
     * 更新维修工单
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintenOrder(@Valid MaintenOrderSaveReqVO updateReqVO);

    /**
     * 删除维修工单
     *
     * @param id 编号
     */
    void deleteMaintenOrder(Long id);

    /**
     * 获得维修工单
     *
     * @param id 编号
     * @return 维修工单
     */
    MaintenOrderDO getMaintenOrder(Long id);

    /**
     * 获得维修工单分页
     *
     * @param pageReqVO 分页查询
     * @return 维修工单分页
     */
    PageResult<MaintenOrderDO> getMaintenOrderPage(MaintenOrderPageReqVO pageReqVO);

    // ==================== 子表（维修工单设备关联） ====================

    /**
     * 获得维修工单设备关联列表
     *
     * @param workOrderId 维修工单ID
     * @return 维修工单设备关联列表
     */
    List<MaintenOrderDeviceDO> getMaintenOrderDeviceListByWorkOrderId(Long workOrderId);

    // ==================== 子表（工单配件） ====================

    /**
     * 获得工单配件列表
     *
     * @param workOrderId 维修工单ID
     * @return 工单配件列表
     */
    List<MaintenOrderPartDO> getMaintenOrderPartListByWorkOrderId(Long workOrderId);

}