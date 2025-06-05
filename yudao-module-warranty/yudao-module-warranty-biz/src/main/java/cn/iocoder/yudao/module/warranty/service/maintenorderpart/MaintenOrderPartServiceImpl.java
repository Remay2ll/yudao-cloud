package cn.iocoder.yudao.module.warranty.service.maintenorderpart;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorderpart.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderpart.MaintenOrderPartDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.maintenorderpart.MaintenOrderPartMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 工单配件 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class MaintenOrderPartServiceImpl implements MaintenOrderPartService {

    @Resource
    private MaintenOrderPartMapper maintenOrderPartMapper;

    @Override
    public Long createMaintenOrderPart(MaintenOrderPartSaveReqVO createReqVO) {
        // 插入
        MaintenOrderPartDO maintenOrderPart = BeanUtils.toBean(createReqVO, MaintenOrderPartDO.class);
        maintenOrderPartMapper.insert(maintenOrderPart);
        // 返回
        return maintenOrderPart.getId();
    }

    @Override
    public void updateMaintenOrderPart(MaintenOrderPartSaveReqVO updateReqVO) {
        // 校验存在
        validateMaintenOrderPartExists(updateReqVO.getId());
        // 更新
        MaintenOrderPartDO updateObj = BeanUtils.toBean(updateReqVO, MaintenOrderPartDO.class);
        maintenOrderPartMapper.updateById(updateObj);
    }

    @Override
    public void deleteMaintenOrderPart(Long id) {
        // 校验存在
        validateMaintenOrderPartExists(id);
        // 删除
        maintenOrderPartMapper.deleteById(id);
    }

    private void validateMaintenOrderPartExists(Long id) {
        if (maintenOrderPartMapper.selectById(id) == null) {
            throw exception(MAINTEN_ORDER_PART_NOT_EXISTS);
        }
    }

    @Override
    public MaintenOrderPartDO getMaintenOrderPart(Long id) {
        return maintenOrderPartMapper.selectById(id);
    }

    @Override
    public PageResult<MaintenOrderPartDO> getMaintenOrderPartPage(MaintenOrderPartPageReqVO pageReqVO) {
        return maintenOrderPartMapper.selectPage(pageReqVO);
    }

}