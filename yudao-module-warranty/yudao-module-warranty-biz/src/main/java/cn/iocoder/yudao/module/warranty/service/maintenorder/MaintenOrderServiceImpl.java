package cn.iocoder.yudao.module.warranty.service.maintenorder;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorder.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorder.MaintenOrderDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderdevice.MaintenOrderDeviceDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderpart.MaintenOrderPartDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.maintenorder.MaintenOrderMapper;
import cn.iocoder.yudao.module.warranty.dal.mysql.maintenorderdevice.MaintenOrderDeviceMapper;
import cn.iocoder.yudao.module.warranty.dal.mysql.maintenorderpart.MaintenOrderPartMapper;
import cn.iocoder.yudao.module.warranty.service.issuetype.IssueTypeService;
import cn.iocoder.yudao.module.warranty.dal.dataobject.issuetype.IssueTypeDO;
import cn.hutool.core.util.StrUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 维修工单 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class MaintenOrderServiceImpl implements MaintenOrderService {

    @Resource
    private MaintenOrderMapper maintenOrderMapper;
    @Resource
    private MaintenOrderDeviceMapper maintenOrderDeviceMapper;
    @Resource
    private MaintenOrderPartMapper maintenOrderPartMapper;
    @Resource
    private IssueTypeService issueTypeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMaintenOrder(MaintenOrderSaveReqVO createReqVO) {
        // 插入
        MaintenOrderDO maintenOrder = BeanUtils.toBean(createReqVO, MaintenOrderDO.class);
        // 生成工单编号（如果没有提供）
        if (StrUtil.isBlank(maintenOrder.getWorkOrderNo())) {
            maintenOrder.setWorkOrderNo(generateWorkOrderNo());
        }
        // 根据故障类型自动填充相关信息
        fillIssueTypeInfo(maintenOrder);
        maintenOrderMapper.insert(maintenOrder);

        // 插入子表
        createMaintenOrderDeviceList(maintenOrder.getId(), createReqVO.getMaintenOrderDevices());
        createMaintenOrderPartList(maintenOrder.getId(), createReqVO.getMaintenOrderParts());
        // 返回
        return maintenOrder.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMaintenOrder(MaintenOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateMaintenOrderExists(updateReqVO.getId());
        // 更新
        MaintenOrderDO updateObj = BeanUtils.toBean(updateReqVO, MaintenOrderDO.class);
        // 根据故障类型自动填充相关信息
        fillIssueTypeInfo(updateObj);
        maintenOrderMapper.updateById(updateObj);

        // 更新子表
        updateMaintenOrderDeviceList(updateReqVO.getId(), updateReqVO.getMaintenOrderDevices());
        updateMaintenOrderPartList(updateReqVO.getId(), updateReqVO.getMaintenOrderParts());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMaintenOrder(Long id) {
        // 校验存在
        validateMaintenOrderExists(id);
        // 删除
        maintenOrderMapper.deleteById(id);

        // 删除子表
        deleteMaintenOrderDeviceByWorkOrderId(id);
        deleteMaintenOrderPartByWorkOrderId(id);
    }

    private void validateMaintenOrderExists(Long id) {
        if (maintenOrderMapper.selectById(id) == null) {
            throw exception(MAINTEN_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public MaintenOrderDO getMaintenOrder(Long id) {
        return maintenOrderMapper.selectById(id);
    }

    @Override
    public PageResult<MaintenOrderDO> getMaintenOrderPage(MaintenOrderPageReqVO pageReqVO) {
        return maintenOrderMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（维修工单设备关联） ====================

    @Override
    public List<MaintenOrderDeviceDO> getMaintenOrderDeviceListByWorkOrderId(Long workOrderId) {
        return maintenOrderDeviceMapper.selectListByWorkOrderId(workOrderId);
    }

    private void createMaintenOrderDeviceList(Long workOrderId, List<MaintenOrderDeviceDO> list) {
        list.forEach(o -> o.setWorkOrderId(workOrderId));
        maintenOrderDeviceMapper.insertBatch(list);
    }

    private void updateMaintenOrderDeviceList(Long workOrderId, List<MaintenOrderDeviceDO> list) {
        deleteMaintenOrderDeviceByWorkOrderId(workOrderId);
		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createMaintenOrderDeviceList(workOrderId, list);
    }

    private void deleteMaintenOrderDeviceByWorkOrderId(Long workOrderId) {
        maintenOrderDeviceMapper.deleteByWorkOrderId(workOrderId);
    }

    // ==================== 子表（工单配件） ====================

    @Override
    public List<MaintenOrderPartDO> getMaintenOrderPartListByWorkOrderId(Long workOrderId) {
        return maintenOrderPartMapper.selectListByWorkOrderId(workOrderId);
    }

    private void createMaintenOrderPartList(Long workOrderId, List<MaintenOrderPartDO> list) {
        list.forEach(o -> o.setWorkOrderId(workOrderId));
        maintenOrderPartMapper.insertBatch(list);
    }

    private void updateMaintenOrderPartList(Long workOrderId, List<MaintenOrderPartDO> list) {
        deleteMaintenOrderPartByWorkOrderId(workOrderId);
		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createMaintenOrderPartList(workOrderId, list);
    }

    private void deleteMaintenOrderPartByWorkOrderId(Long workOrderId) {
        maintenOrderPartMapper.deleteByWorkOrderId(workOrderId);
    }

    /**
     * 生成工单编号
     * 格式：WO + yyyyMMdd + HHmmss + 3位随机数
     *
     * @return 工单编号
     */
    private String generateWorkOrderNo() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomNum = String.format("%03d", (int) (Math.random() * 1000));
        return "WO" + dateTime + randomNum;
    }

    /**
     * 根据故障类型ID自动填充故障类型名称和预计处理时长
     *
     * @param maintenOrder 工单对象
     */
    private void fillIssueTypeInfo(MaintenOrderDO maintenOrder) {
        if (maintenOrder.getIssueTypeId() != null) {
            IssueTypeDO issueType = issueTypeService.getIssueType(maintenOrder.getIssueTypeId());
            if (issueType != null) {
                // 填充故障类型名称
                maintenOrder.setIssueTypeName(issueType.getTypeName());
                // 如果工单中没有设置预计处理时长，则使用故障类型的默认时长
                if (maintenOrder.getEstimatedDuration() == null) {
                    maintenOrder.setEstimatedDuration(issueType.getExpectedDuration());
                }
            }
        }
    }

}