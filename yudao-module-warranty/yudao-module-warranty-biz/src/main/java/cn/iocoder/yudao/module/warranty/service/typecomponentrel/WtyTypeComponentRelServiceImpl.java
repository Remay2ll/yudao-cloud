package cn.iocoder.yudao.module.warranty.service.typecomponentrel;

import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDoWithCompName;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.typecomponentrel.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.typecomponentrel.WtyTypeComponentRelMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 设备类型-配件关联 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class WtyTypeComponentRelServiceImpl implements WtyTypeComponentRelService {

    @Resource
    private WtyTypeComponentRelMapper wtyTypeComponentRelMapper;

    @Override
    public Long createWtyTypeComponentRel(WtyTypeComponentRelSaveReqVO createReqVO) {
        // 插入
        WtyTypeComponentRelDO wtyTypeComponentRel = BeanUtils.toBean(createReqVO, WtyTypeComponentRelDO.class);
        wtyTypeComponentRelMapper.insert(wtyTypeComponentRel);
        // 返回
        return wtyTypeComponentRel.getId();
    }

    @Override
    public void updateWtyTypeComponentRel(WtyTypeComponentRelSaveReqVO updateReqVO) {
        // 校验存在
        validateWtyTypeComponentRelExists(updateReqVO.getId());
        // 更新
        WtyTypeComponentRelDO updateObj = BeanUtils.toBean(updateReqVO, WtyTypeComponentRelDO.class);
        wtyTypeComponentRelMapper.updateById(updateObj);
    }

    @Override
    public void deleteWtyTypeComponentRel(Long id) {
        // 校验存在
        validateWtyTypeComponentRelExists(id);
        // 删除
        wtyTypeComponentRelMapper.deleteById(id);
    }

    private void validateWtyTypeComponentRelExists(Long id) {
        if (wtyTypeComponentRelMapper.selectById(id) == null) {
            throw exception(WTY_TYPE_COMPONENT_REL_NOT_EXISTS);
        }
    }

    @Override
    public WtyTypeComponentRelDO getWtyTypeComponentRel(Long id) {
        return wtyTypeComponentRelMapper.selectById(id);
    }

    @Override
    public PageResult<WtyTypeComponentRelDO> getWtyTypeComponentRelPage(WtyTypeComponentRelPageReqVO pageReqVO) {
        return wtyTypeComponentRelMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<WtyTypeComponentRelDoWithCompName> getWtyTypeComponentRelPageWithCompName(WtyTypeComponentRelPageReqVO pageReqVO) {
        return wtyTypeComponentRelMapper.selectPageJoinCompName(pageReqVO);
    }

}