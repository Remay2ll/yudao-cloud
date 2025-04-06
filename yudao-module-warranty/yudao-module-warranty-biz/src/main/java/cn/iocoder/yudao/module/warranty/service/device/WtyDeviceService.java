package cn.iocoder.yudao.module.warranty.service.device;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.device.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.device.WtyDeviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备 Service 接口
 *
 * @author Remay
 */
public interface WtyDeviceService {

    /**
     * 创建设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWtyDevice(@Valid WtyDeviceSaveReqVO createReqVO);

    /**
     * 更新设备
     *
     * @param updateReqVO 更新信息
     */
    void updateWtyDevice(@Valid WtyDeviceSaveReqVO updateReqVO);

    /**
     * 删除设备
     *
     * @param id 编号
     */
    void deleteWtyDevice(Long id);

    /**
     * 获得设备
     *
     * @param id 编号
     * @return 设备
     */
    WtyDeviceDO getWtyDevice(Long id);

    /**
     * 获得设备分页
     *
     * @param pageReqVO 分页查询
     * @return 设备分页
     */
    PageResult<WtyDeviceDO> getWtyDevicePage(WtyDevicePageReqVO pageReqVO);

}