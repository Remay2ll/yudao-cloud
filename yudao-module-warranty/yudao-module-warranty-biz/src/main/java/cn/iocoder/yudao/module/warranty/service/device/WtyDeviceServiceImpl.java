package cn.iocoder.yudao.module.warranty.service.device;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.device.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.device.WtyDeviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.device.WtyDeviceMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 设备 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class WtyDeviceServiceImpl implements WtyDeviceService {

    @Resource
    private WtyDeviceMapper wtyDeviceMapper;

    @Override
    public Long createWtyDevice(WtyDeviceSaveReqVO createReqVO) {
        // 插入
        WtyDeviceDO wtyDevice = BeanUtils.toBean(createReqVO, WtyDeviceDO.class);
        wtyDeviceMapper.insert(wtyDevice);
        // 返回
        return wtyDevice.getId();
    }

    @Override
    public void updateWtyDevice(WtyDeviceSaveReqVO updateReqVO) {
        // 校验存在
        validateWtyDeviceExists(updateReqVO.getId());
        // 更新
        WtyDeviceDO updateObj = BeanUtils.toBean(updateReqVO, WtyDeviceDO.class);
        wtyDeviceMapper.updateById(updateObj);
    }

    @Override
    public void deleteWtyDevice(Long id) {
        // 校验存在
        validateWtyDeviceExists(id);
        // 删除
        wtyDeviceMapper.deleteById(id);
    }

    private void validateWtyDeviceExists(Long id) {
        if (wtyDeviceMapper.selectById(id) == null) {
            throw exception(WTY_DEVICE_NOT_EXISTS);
        }
    }

    @Override
    public WtyDeviceDO getWtyDevice(Long id) {
        return wtyDeviceMapper.selectById(id);
    }

    @Override
    public PageResult<WtyDeviceDO> getWtyDevicePage(WtyDevicePageReqVO pageReqVO) {
        return wtyDeviceMapper.selectPage(pageReqVO);
    }

}