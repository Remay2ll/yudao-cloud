package cn.iocoder.yudao.module.warranty.service.maintenorderdevice;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorderdevice.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice.MaintenOrderDeviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.maintenorderdevice.MaintenOrderDeviceMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 维修工单设备关联 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class MaintenOrderDeviceServiceImpl implements MaintenOrderDeviceService {

    @Resource
    private MaintenOrderDeviceMapper maintenOrderDeviceMapper;

    @Override
    public Long createMaintenOrderDevice(MaintenOrderDeviceSaveReqVO createReqVO) {
        // 插入
        MaintenOrderDeviceDO maintenOrderDevice = BeanUtils.toBean(createReqVO, MaintenOrderDeviceDO.class);
        maintenOrderDeviceMapper.insert(maintenOrderDevice);
        // 返回
        return maintenOrderDevice.getId();
    }

    @Override
    public void updateMaintenOrderDevice(MaintenOrderDeviceSaveReqVO updateReqVO) {
        // 校验存在
        validateMaintenOrderDeviceExists(updateReqVO.getId());
        // 更新
        MaintenOrderDeviceDO updateObj = BeanUtils.toBean(updateReqVO, MaintenOrderDeviceDO.class);
        maintenOrderDeviceMapper.updateById(updateObj);
    }

    @Override
    public void deleteMaintenOrderDevice(Long id) {
        // 校验存在
        validateMaintenOrderDeviceExists(id);
        // 删除
        maintenOrderDeviceMapper.deleteById(id);
    }

    private void validateMaintenOrderDeviceExists(Long id) {
        if (maintenOrderDeviceMapper.selectById(id) == null) {
            throw exception(MAINTEN_ORDER_DEVICE_NOT_EXISTS);
        }
    }

    @Override
    public MaintenOrderDeviceDO getMaintenOrderDevice(Long id) {
        return maintenOrderDeviceMapper.selectById(id);
    }

    @Override
    public PageResult<MaintenOrderDeviceDO> getMaintenOrderDevicePage(MaintenOrderDevicePageReqVO pageReqVO) {
        return maintenOrderDeviceMapper.selectPage(pageReqVO);
    }

}