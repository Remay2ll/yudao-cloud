package cn.iocoder.yudao.module.warranty.service.maintenorderdevice;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorderdevice.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice.MaintenOrderDeviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 维修工单设备关联 Service 接口
 *
 * @author Remay
 */
public interface MaintenOrderDeviceService {

    /**
     * 创建维修工单设备关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaintenOrderDevice(@Valid MaintenOrderDeviceSaveReqVO createReqVO);

    /**
     * 更新维修工单设备关联
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintenOrderDevice(@Valid MaintenOrderDeviceSaveReqVO updateReqVO);

    /**
     * 删除维修工单设备关联
     *
     * @param id 编号
     */
    void deleteMaintenOrderDevice(Long id);

    /**
     * 获得维修工单设备关联
     *
     * @param id 编号
     * @return 维修工单设备关联
     */
    MaintenOrderDeviceDO getMaintenOrderDevice(Long id);

    /**
     * 获得维修工单设备关联分页
     *
     * @param pageReqVO 分页查询
     * @return 维修工单设备关联分页
     */
    PageResult<MaintenOrderDeviceDO> getMaintenOrderDevicePage(MaintenOrderDevicePageReqVO pageReqVO);

}