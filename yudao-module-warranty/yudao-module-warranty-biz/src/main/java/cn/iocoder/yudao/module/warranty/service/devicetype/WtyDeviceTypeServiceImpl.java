package cn.iocoder.yudao.module.warranty.service.devicetype;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype.WtyDeviceTypeDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.devicetype.WtyDeviceTypeMapper;
import cn.iocoder.yudao.module.warranty.dal.mysql.typecomponentrel.WtyTypeComponentRelMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 设备类型 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class WtyDeviceTypeServiceImpl implements WtyDeviceTypeService {

    @Resource
    private WtyDeviceTypeMapper wtyDeviceTypeMapper;
    @Resource
    private WtyTypeComponentRelMapper wtyTypeComponentRelMapper;

    @Override
    public Long createWtyDeviceType(WtyDeviceTypeSaveReqVO createReqVO) {
        // 插入
        WtyDeviceTypeDO wtyDeviceType = BeanUtils.toBean(createReqVO, WtyDeviceTypeDO.class);
        wtyDeviceTypeMapper.insert(wtyDeviceType);
        // 返回
        return wtyDeviceType.getId();
    }

    @Override
    public void updateWtyDeviceType(WtyDeviceTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateWtyDeviceTypeExists(updateReqVO.getId());
        // 更新
        WtyDeviceTypeDO updateObj = BeanUtils.toBean(updateReqVO, WtyDeviceTypeDO.class);
        wtyDeviceTypeMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWtyDeviceType(Long id) {
        // 校验存在
        validateWtyDeviceTypeExists(id);
        // 删除
        wtyDeviceTypeMapper.deleteById(id);

        // 删除子表
        deleteWtyTypeComponentRelByTypeId(id);
    }

    private void validateWtyDeviceTypeExists(Long id) {
        if (wtyDeviceTypeMapper.selectById(id) == null) {
            throw exception(WTY_DEVICE_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public WtyDeviceTypeDO getWtyDeviceType(Long id) {
        return wtyDeviceTypeMapper.selectById(id);
    }

    @Override
    public PageResult<WtyDeviceTypeDO> getWtyDeviceTypePage(WtyDeviceTypePageReqVO pageReqVO) {
        return wtyDeviceTypeMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（设备类型-配件关联） ====================

    @Override
    public PageResult<WtyTypeComponentRelDO> getWtyTypeComponentRelPage(PageParam pageReqVO, Long typeId) {
        return wtyTypeComponentRelMapper.selectPage(pageReqVO, typeId);
    }

    @Override
    public Long createWtyTypeComponentRel(WtyTypeComponentRelDO wtyTypeComponentRel) {
        wtyTypeComponentRelMapper.insert(wtyTypeComponentRel);
        return wtyTypeComponentRel.getId();
    }

    @Override
    public void updateWtyTypeComponentRel(WtyTypeComponentRelDO wtyTypeComponentRel) {
        // 校验存在
        validateWtyTypeComponentRelExists(wtyTypeComponentRel.getId());
        // 更新
        wtyTypeComponentRel.setUpdater(null).setUpdateTime(null); // 解决更新情况下：updateTime 不更新
        wtyTypeComponentRelMapper.updateById(wtyTypeComponentRel);
    }

    @Override
    public void deleteWtyTypeComponentRel(Long id) {
        // 校验存在
        validateWtyTypeComponentRelExists(id);
        // 删除
        wtyTypeComponentRelMapper.deleteById(id);
    }

    @Override
    public WtyTypeComponentRelDO getWtyTypeComponentRel(Long id) {
        return wtyTypeComponentRelMapper.selectById(id);
    }

    private void validateWtyTypeComponentRelExists(Long id) {
        if (wtyTypeComponentRelMapper.selectById(id) == null) {
            throw exception(WTY_TYPE_COMPONENT_REL_NOT_EXISTS);
        }
    }

    private void deleteWtyTypeComponentRelByTypeId(Long typeId) {
        wtyTypeComponentRelMapper.deleteByTypeId(typeId);
    }

}