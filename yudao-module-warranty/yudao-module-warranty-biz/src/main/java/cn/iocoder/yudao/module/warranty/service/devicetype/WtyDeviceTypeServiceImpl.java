package cn.iocoder.yudao.module.warranty.service.devicetype;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype.WtyDeviceTypeWithPNameDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDoWithCompName;
import cn.iocoder.yudao.module.warranty.enums.common.CommonAuditStatus;
import cn.iocoder.yudao.module.warranty.enums.common.CommonStatus;
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
        // 校验父类型ID的有效性
        validateParentWtyDeviceType(null, createReqVO.getParentTypeId());
        // 校验类型名称的唯一性
        validateWtyDeviceTypeTypeNameUnique(null, createReqVO.getParentTypeId(), createReqVO.getTypeName());
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
        // 校验父类型ID的有效性
        validateParentWtyDeviceType(updateReqVO.getId(), updateReqVO.getParentTypeId());
        // 校验类型名称的唯一性
        validateWtyDeviceTypeTypeNameUnique(updateReqVO.getId(), updateReqVO.getParentTypeId(), updateReqVO.getTypeName());
        // 更新
        WtyDeviceTypeDO updateObj = BeanUtils.toBean(updateReqVO, WtyDeviceTypeDO.class);
        wtyDeviceTypeMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWtyDeviceType(Long id) {
        // 校验存在
        validateWtyDeviceTypeExists(id);
        // 校验是否有子设备类型
        if (wtyDeviceTypeMapper.selectCountByParentTypeId(id) > 0) {
            throw exception(WTY_DEVICE_TYPE_EXITS_CHILDREN);
        }
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

    private void validateParentWtyDeviceType(Long id, Long parentTypeId) {
        if (parentTypeId == null || WtyDeviceTypeDO.PARENT_TYPE_ID_ROOT.equals(parentTypeId)) {
            return;
        }
        // 1. 不能设置自己为父设备类型
        if (Objects.equals(id, parentTypeId)) {
            throw exception(WTY_DEVICE_TYPE_PARENT_ERROR);
        }
        // 2. 父设备类型不存在
        WtyDeviceTypeDO parentWtyDeviceType = wtyDeviceTypeMapper.selectById(parentTypeId);
        if (parentWtyDeviceType == null) {
            throw exception(WTY_DEVICE_TYPE_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父设备类型，如果父设备类型是自己的子设备类型，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentTypeId = parentWtyDeviceType.getParentTypeId();
            if (Objects.equals(id, parentTypeId)) {
                throw exception(WTY_DEVICE_TYPE_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父设备类型
            if (parentTypeId == null || WtyDeviceTypeDO.PARENT_TYPE_ID_ROOT.equals(parentTypeId)) {
                break;
            }
            parentWtyDeviceType = wtyDeviceTypeMapper.selectById(parentTypeId);
            if (parentWtyDeviceType == null) {
                break;
            }
        }
    }

    private void validateWtyDeviceTypeTypeNameUnique(Long id, Long parentTypeId, String typeName) {
        WtyDeviceTypeDO wtyDeviceType = wtyDeviceTypeMapper.selectByParentTypeIdAndTypeName(parentTypeId, typeName);
        if (wtyDeviceType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的设备类型
        if (id == null) {
            throw exception(WTY_DEVICE_TYPE_TYPE_NAME_DUPLICATE);
        }
        if (!Objects.equals(wtyDeviceType.getId(), id)) {
            throw exception(WTY_DEVICE_TYPE_TYPE_NAME_DUPLICATE);
        }
    }

    @Override
    public WtyDeviceTypeDO getWtyDeviceType(Long id) {
        return wtyDeviceTypeMapper.selectById(id);
    }

    @Override
    public List<WtyDeviceTypeDO> getWtyDeviceTypeList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return wtyDeviceTypeMapper.selectList(new LambdaQueryWrapperX<WtyDeviceTypeDO>().in(WtyDeviceTypeDO::getId, ids));
    }

    @Override
    public PageResult<WtyDeviceTypeDO> getWtyDeviceTypePage(WtyDeviceTypePageReqVO pageReqVO) {
        return wtyDeviceTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<WtyDeviceTypeWithPNameDO> getWtyDeviceTypePageWithP(WtyDeviceTypePageReqVO pageReqVO) {
        return wtyDeviceTypeMapper.selectPageJoinPName(pageReqVO);
    }

    @Override
    public List<WtyDeviceTypeDO> getWtyDeviceTypeTree(WtyDeviceTypeTreeReqVO reqVO) {
        List<WtyDeviceTypeDO> wtyDeviceTypeDOS=new ArrayList<>();
        List<Long> parentTypeIds=reqVO.getParentTypeIds();
        for(int i=0;i<Short.MAX_VALUE;i++) {
            List<Long> finalParentTypeIds = parentTypeIds;
            List<WtyDeviceTypeDO> tempResult=wtyDeviceTypeMapper.selectList(new LambdaQueryWrapperX<WtyDeviceTypeDO>()
                    .eq(WtyDeviceTypeDO::getStatus, CommonStatus.ENABLE.getStatus())
                    .and(wrapper ->{
                        if(finalParentTypeIds==null||finalParentTypeIds.isEmpty()){
                            wrapper.isNull(WtyDeviceTypeDO::getParentTypeId);
                        }else {
                            wrapper.in(WtyDeviceTypeDO::getParentTypeId, finalParentTypeIds);
                        }
                    }));
            wtyDeviceTypeDOS.addAll(tempResult);
            if(tempResult.isEmpty()) {
                break;
            }
            parentTypeIds=tempResult.stream().map(WtyDeviceTypeDO::getId).toList();
        }
        return wtyDeviceTypeDOS;
    }

    @Override
    public List<WtyDeviceTypeDO> getWtyDeviceTypeTreeOneLevel(WtyDeviceTypeTreeReqVO reqVO) {
        List<WtyDeviceTypeDO> tempResult=wtyDeviceTypeMapper.selectList(new LambdaQueryWrapperX<WtyDeviceTypeDO>()
                .eq(WtyDeviceTypeDO::getStatus, CommonStatus.ENABLE.getStatus())
                .eq(WtyDeviceTypeDO::getIsAudit, CommonAuditStatus.APPROVED.getAuditStatus())
                .and(wrapper ->{
                    if(reqVO.getParentTypeIds()==null||reqVO.getParentTypeIds().isEmpty()){
                        wrapper.isNull(WtyDeviceTypeDO::getParentTypeId);
                    }else {
                        wrapper.in(WtyDeviceTypeDO::getParentTypeId, reqVO.getParentTypeIds());
                    }
                }));
        return new ArrayList<>(tempResult);
    }

    // ==================== 子表（设备类型-配件关联） ====================

    @Override
    public PageResult<WtyTypeComponentRelDO> getWtyTypeComponentRelPage(PageParam pageReqVO, Long typeId) {
        return wtyTypeComponentRelMapper.selectPage(pageReqVO, typeId);
    }

    @Override
    public PageResult<WtyTypeComponentRelDoWithCompName> getWtyTypeComponentRelPageWithCompName(PageParam pageReqVO, Long typeId) {
        return wtyTypeComponentRelMapper.selectPageJoinCompName(pageReqVO, typeId);
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