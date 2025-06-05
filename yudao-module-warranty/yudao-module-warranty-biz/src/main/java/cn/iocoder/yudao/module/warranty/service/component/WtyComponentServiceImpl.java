package cn.iocoder.yudao.module.warranty.service.component;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.warranty.enums.common.CommonAuditStatus;
import cn.iocoder.yudao.module.warranty.enums.common.CommonStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.component.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.component.WtyComponentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.component.WtyComponentMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 配件 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class WtyComponentServiceImpl implements WtyComponentService {

    @Resource
    private WtyComponentMapper wtyComponentMapper;

    @Override
    public Long createWtyComponent(WtyComponentSaveReqVO createReqVO) {
        // 插入
        WtyComponentDO wtyComponent = BeanUtils.toBean(createReqVO, WtyComponentDO.class);
        wtyComponentMapper.insert(wtyComponent);
        // 返回
        return wtyComponent.getId();
    }

    @Override
    public void updateWtyComponent(WtyComponentSaveReqVO updateReqVO) {
        // 校验存在
        validateWtyComponentExists(updateReqVO.getId());
        // 更新
        WtyComponentDO updateObj = BeanUtils.toBean(updateReqVO, WtyComponentDO.class);
        wtyComponentMapper.updateById(updateObj);
    }

    @Override
    public void deleteWtyComponent(Long id) {
        // 校验存在
        validateWtyComponentExists(id);
        // 删除
        wtyComponentMapper.deleteById(id);
    }

    private void validateWtyComponentExists(Long id) {
        if (wtyComponentMapper.selectById(id) == null) {
            throw exception(WTY_COMPONENT_NOT_EXISTS);
        }
    }

    @Override
    public WtyComponentDO getWtyComponent(Long id) {
        return wtyComponentMapper.selectById(id);
    }

    @Override
    public PageResult<WtyComponentDO> getWtyComponentPage(WtyComponentPageReqVO pageReqVO) {
        return wtyComponentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<WtyComponentDO> getWtyComponentList() {
        return wtyComponentMapper.selectList(new LambdaQueryWrapperX<WtyComponentDO>()
                .eq(WtyComponentDO::getStatus, CommonStatus.ENABLE.getStatus())
                .eq(WtyComponentDO::getIsAudit, CommonAuditStatus.APPROVED.getAuditStatus())
                .orderByAsc(WtyComponentDO::getId));
    }

}