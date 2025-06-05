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
    WtyDeviceRespVO getWtyDevice(Long id);

    /**
     * 根据设备编码获得设备
     *
     * @param deviceCode 设备编码
     * @return 设备
     */
    WtyDeviceRespVO getWtyDeviceByCode(String deviceCode);

    /**
     * 获得设备分页
     *
     * @param pageReqVO 分页查询
     * @return 设备分页
     */
    PageResult<WtyDeviceRespVO> getWtyDevicePage(WtyDevicePageReqVO pageReqVO);

    /**
     * 根据网点ID获取该网点及其下属网点下的所有设备列表（不分页）
     *
     * @param deptId 网点ID
     * @param status 设备状态 (可选, 为 null 则查询所有状态或仅启用，具体逻辑在实现中定义)
     * @return 设备列表
     */
    List<WtyDeviceRespVO> getWtyDeviceListByDeptIdRecursive(Long deptId, Integer status);

}