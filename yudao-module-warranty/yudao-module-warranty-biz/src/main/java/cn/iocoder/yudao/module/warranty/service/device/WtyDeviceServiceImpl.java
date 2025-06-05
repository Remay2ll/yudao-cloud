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
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.hutool.core.collection.CollUtil;

import cn.iocoder.yudao.module.warranty.dal.mysql.device.WtyDeviceMapper;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype.WtyDeviceTypeDO;
import cn.iocoder.yudao.module.warranty.service.devicetype.WtyDeviceTypeService;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

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

    @Resource
    private DeptApi deptApi;

    @Resource
    private WtyDeviceTypeService wtyDeviceTypeService;

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
    public WtyDeviceRespVO getWtyDevice(Long id) {
        // 修改为调用新的 mapper 方法，该方法会返回包含 typeName 和 deptName 的设备信息
        WtyDeviceDO deviceDO = wtyDeviceMapper.selectByIdWithDetails(id);
        if (deviceDO == null) {
            return null;
        }
        // 转换，此时 deviceDO 已包含 typeName 和 deptName
        return BeanUtils.toBean(deviceDO, WtyDeviceRespVO.class);
    }

    @Override
    public WtyDeviceRespVO getWtyDeviceByCode(String deviceCode) {
        // 修改为调用新的 mapper 方法
        WtyDeviceDO deviceDO = wtyDeviceMapper.selectOneByDeviceCodeWithDetails(deviceCode);
        if (deviceDO == null) {
            return null;
        }
        // 转换
        return BeanUtils.toBean(deviceDO, WtyDeviceRespVO.class);
    }

    @Override
    public PageResult<WtyDeviceRespVO> getWtyDevicePage(WtyDevicePageReqVO pageReqVO) {
        PageResult<WtyDeviceDO> pageResultDO = wtyDeviceMapper.selectPage(pageReqVO);
        if (CollUtil.isEmpty(pageResultDO.getList())) {
            return PageResult.empty(pageResultDO.getTotal());
        }

        // 批量获取关联信息
        List<WtyDeviceDO> deviceDOs = pageResultDO.getList();
        Set<Long> typeIds = CollectionUtils.convertSet(deviceDOs, WtyDeviceDO::getTypeId);
        Set<Long> deptIdsForPage = CollectionUtils.convertSet(deviceDOs, WtyDeviceDO::getDeptId);

        Map<Long, String> typeMap = new HashMap<>();
        if (CollUtil.isNotEmpty(typeIds)) {
            List<WtyDeviceTypeDO> deviceTypes = wtyDeviceTypeService.getWtyDeviceTypeList(typeIds);
            typeMap = CollectionUtils.convertMap(deviceTypes, WtyDeviceTypeDO::getId, WtyDeviceTypeDO::getTypeName);
        }

        Map<Long, String> deptMap = new HashMap<>();
        if (CollUtil.isNotEmpty(deptIdsForPage)) {
            List<DeptRespDTO> deptDtoList = deptApi.getDeptList(deptIdsForPage).getCheckedData();
            deptMap = CollectionUtils.convertMap(deptDtoList, DeptRespDTO::getId, DeptRespDTO::getName);
        }

        List<WtyDeviceRespVO> respVOList = new ArrayList<>(deviceDOs.size());
        for (WtyDeviceDO deviceDO : deviceDOs) {
            WtyDeviceRespVO respVO = BeanUtils.toBean(deviceDO, WtyDeviceRespVO.class);
            respVO.setTypeName(typeMap.get(deviceDO.getTypeId()));
            respVO.setDeptName(deptMap.get(deviceDO.getDeptId()));
            respVOList.add(respVO);
        }
        return new PageResult<>(respVOList, pageResultDO.getTotal());
    }

    @Override
    public List<WtyDeviceRespVO> getWtyDeviceListByDeptIdRecursive(Long deptId, Integer deviceStatus) {
        // 1. 确定部门状态和类型查询条件
        Integer queryDeptStatus = CommonStatusEnum.ENABLE.getStatus(); // 默认查询启用的部门
        // Integer queryDeptType = YOUR_NET_POINT_TYPE; // 如果需要按部门类型过滤，请设置此值
        // 假设我们暂时不按部门类型过滤，或者 DeptRespDTO 不包含 type 字段，或者 getDeptWithChildrenFiltered 内部不处理 type=null 的情况
        Integer queryDeptType = null;

        // 2. 调用新的API，获取根部门及所有符合条件的子部门
        List<DeptRespDTO> deptDtoList = deptApi.getDeptWithChildrenFiltered(deptId, queryDeptStatus, queryDeptType)
                                            .getCheckedData();

        if (CollUtil.isEmpty(deptDtoList)) {
            return Collections.emptyList();
        }

        Set<Long> collectedDeptIds = deptDtoList.stream()
                                           .map(DeptRespDTO::getId)
                                           .collect(Collectors.toSet());

        if (CollUtil.isEmpty(collectedDeptIds)) {
            return Collections.emptyList();
        }

        // 3. 确定设备状态查询条件
        Integer queryDeviceStatus = (deviceStatus != null) ? deviceStatus : CommonStatusEnum.ENABLE.getStatus();

        // 4. 根据部门ID列表和状态查询设备
        List<WtyDeviceDO> deviceDOs = wtyDeviceMapper.selectListByDeptIdsAndStatus(collectedDeptIds, queryDeviceStatus);
        if (CollUtil.isEmpty(deviceDOs)) {
            return Collections.emptyList();
        }

        // 5. 批量获取关联信息并进行转换
        // 5.1 收集 typeIds 和 deptIds (虽然 deptId 已经从 collectedDeptIds 中获取，但为了与 getWtyDevicePage 逻辑一致性，重新从 deviceDOs 中收集)
        Set<Long> typeIds = CollectionUtils.convertSet(deviceDOs, WtyDeviceDO::getTypeId);
        // 注意：这里的 deptId 实际上是从 deviceDOs 自身取的，确保是最准确的关联部门
        Set<Long> deviceDeptIds = CollectionUtils.convertSet(deviceDOs, WtyDeviceDO::getDeptId);

        // 5.2 批量查询设备类型信息
        Map<Long, String> typeMap = new HashMap<>();
        if (CollUtil.isNotEmpty(typeIds)) {
            List<WtyDeviceTypeDO> deviceTypes = wtyDeviceTypeService.getWtyDeviceTypeList(typeIds);
            typeMap = CollectionUtils.convertMap(deviceTypes, WtyDeviceTypeDO::getId, WtyDeviceTypeDO::getTypeName);
        }

        // 5.3 批量查询部门信息
        Map<Long, String> deptMap = new HashMap<>();
        if (CollUtil.isNotEmpty(deviceDeptIds)) {
            // 注意：确保 deptApi.getDeptList 返回的是有效的 List<DeptRespDTO>
            // 如果 getCheckedData() 可能抛异常或返回 null，需要做相应处理
            List<DeptRespDTO> depts = deptApi.getDeptList(deviceDeptIds).getCheckedData();
            if (CollUtil.isNotEmpty(depts)) {
                deptMap = CollectionUtils.convertMap(depts, DeptRespDTO::getId, DeptRespDTO::getName);
            }
        }
        
        // 5.4 转换并填充额外信息
        List<WtyDeviceRespVO> respVOList = new ArrayList<>(deviceDOs.size());
        for (WtyDeviceDO deviceDO : deviceDOs) {
            WtyDeviceRespVO respVO = BeanUtils.toBean(deviceDO, WtyDeviceRespVO.class);
            respVO.setTypeName(typeMap.get(deviceDO.getTypeId()));
            respVO.setDeptName(deptMap.get(deviceDO.getDeptId())); // 使用从 deviceDO 中获取的 deptId
            respVOList.add(respVO);
        }
        return respVOList;
    }

}