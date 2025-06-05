package cn.iocoder.yudao.module.warranty.service.deptaddress;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.deptaddress.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.deptaddress.DeptAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.deptaddress.DeptAddressMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

// 引入 AreaUtils 和 Area 对象
import cn.iocoder.yudao.framework.ip.core.utils.AreaUtils;
import cn.iocoder.yudao.framework.ip.core.Area;
import cn.iocoder.yudao.framework.ip.core.enums.AreaTypeEnum; // 引入AreaTypeEnum

/**
 * 部门地址 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class DeptAddressServiceImpl implements DeptAddressService {

    @Resource
    private DeptAddressMapper deptAddressMapper;

    @Override
    @Transactional // 确保操作的原子性
    public Long createDeptAddress(DeptAddressSaveReqVO createReqVO) {
        DeptAddressDO deptAddress = BeanUtils.toBean(createReqVO, DeptAddressDO.class);
        // 根据 areaId 填充省市区信息
        populateAddressFieldsFromAreaId(deptAddress, createReqVO.getAreaId());

        // 如果将新地址设置为默认，则将该部门其他地址改为非默认
        if (Boolean.TRUE.equals(deptAddress.getIsDefault())) {
            deptAddressMapper.updateNonDefaultByDeptId(deptAddress.getDeptId());
        }

        // 插入
        deptAddressMapper.insert(deptAddress);
        // 返回
        return deptAddress.getId();
    }

    @Override
    @Transactional // 确保操作的原子性
    public void updateDeptAddress(DeptAddressSaveReqVO updateReqVO) {
        // 校验存在
        validateDeptAddressExists(updateReqVO.getId());
        DeptAddressDO updateObj = BeanUtils.toBean(updateReqVO, DeptAddressDO.class);
        // 根据 areaId 填充省市区信息
        populateAddressFieldsFromAreaId(updateObj, updateReqVO.getAreaId());

        // 如果将当前地址设置为默认，则将该部门其他地址改为非默认
        if (Boolean.TRUE.equals(updateObj.getIsDefault())) {
            deptAddressMapper.updateNonDefaultByDeptIdAndExcludeId(updateObj.getDeptId(), updateObj.getId());
        }

        // 更新
        deptAddressMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeptAddress(Long id) {
        // 校验存在
        validateDeptAddressExists(id);
        // 删除
        deptAddressMapper.deleteById(id);
    }

    private void validateDeptAddressExists(Long id) {
        if (deptAddressMapper.selectById(id) == null) {
            throw exception(DEPT_ADDRESS_NOT_EXISTS);
        }
    }

    @Override
    public DeptAddressDO getDeptAddress(Long id) {
        return deptAddressMapper.selectById(id);
    }

    @Override
    public PageResult<DeptAddressDO> getDeptAddressPage(DeptAddressPageReqVO pageReqVO) {
        return deptAddressMapper.selectPage(pageReqVO);
    }

    private void populateAddressFieldsFromAreaId(DeptAddressDO deptAddress, Long areaId) {
        if (areaId == null) {
            // 如果 areaId 为空，可以选择清空相关字段或保持不变
            // deptAddress.setCity(null);
            // deptAddress.setStateProvince(null);
            // deptAddress.setCountry(null); // 一般不需要清空国家
            return;
        }

        Area area = AreaUtils.getArea(areaId.intValue()); // areaId 是 Integer
        if (area == null) {
            // log.warn("无法找到 areaId: {} 对应的地区信息", areaId); // 建议添加日志
            return;
        }

        // 初始化字段，以防之前有值或需要清空
        deptAddress.setCity(null);
        deptAddress.setStateProvince(null);
        // deptAddress.setCountry("中国"); // 通常固定，或根据业务决定是否从地区数据最高层获取

        Area current = area;
        // 从当前区域向上查找，直到省、国家级别或无父级
        // 一般国内地址，我们关心 省、市、区（县）
        // area.getType() 可以参考 AreaTypeEnum: COUNTRY(0), PROVINCE(1), CITY(2), DISTRICT(3), NONE(4);
        
        // 设置国家，通常是中国
        Area countryNode = findParentByType(current, AreaTypeEnum.COUNTRY);
        if (countryNode != null) {
            deptAddress.setCountry(countryNode.getName());
        } else {
            // 如果找不到国家，但 areaId 存在，可能需要一个默认值或进一步的业务判断
            // 对于国内地址，如果 AreaUtils 的数据源是国内的，areaId 对应的层级最高可能到省
            // 也可以直接设置 deptAddress.setCountry("中国"); 如果业务确定只处理国内地址
            if (current.getId() != null && current.getId() > 0 && current.getId() < 1000000) { // 简单判断中国地区ID范围
                 deptAddress.setCountry("中国");
            }
        }


        // 设置区/县名到 streetAddress (如果需要的话，通常 streetAddress 是用户填写的更详细部分)
        // 或者如果 DeptAddressDO 有 district 字段，则设置到 district
        // if (current.getType().equals(AreaTypeEnum.DISTRICT.getType())) {
        //    // deptAddress.setDistrict(current.getName());
        // }

        // 设置市
        Area cityNode = findParentOrSelfByType(current, AreaTypeEnum.CITY);
        if (cityNode != null) {
            deptAddress.setCity(cityNode.getName());
        }
        
        // 设置省
        Area provinceNode = findParentOrSelfByType(current, AreaTypeEnum.PROVINCE);
        if (provinceNode != null) {
            deptAddress.setStateProvince(provinceNode.getName());
        }
        
        // 如果最底层的 areaId 本身就是省或市，也需要填充
        if (area.getType().equals(AreaTypeEnum.PROVINCE.getType())) {
            deptAddress.setStateProvince(area.getName());
        } else if (area.getType().equals(AreaTypeEnum.CITY.getType())) {
            deptAddress.setCity(area.getName());
            // 如果市存在，它的父级通常是省
            if (area.getParent() != null && area.getParent().getType().equals(AreaTypeEnum.PROVINCE.getType())) {
                 deptAddress.setStateProvince(area.getParent().getName());
            }
        }
    }

    // 辅助方法：查找指定类型的父节点（或自身）
    private Area findParentOrSelfByType(Area area, AreaTypeEnum targetType) {
        Area current = area;
        while (current != null) {
            if (current.getType().equals(targetType.getType())) {
                return current;
            }
            if (current.getParent() == null || current.getParent().getId().equals(Area.ID_GLOBAL)) { // 避免无限向上或超出中国范围
                break;
            }
            current = current.getParent();
        }
        return null; // 未找到
    }
    
    // 辅助方法：查找指定类型的父节点
    private Area findParentByType(Area area, AreaTypeEnum targetType) {
        if (area == null || area.getParent() == null || area.getParent().getId().equals(Area.ID_GLOBAL)) {
            return null;
        }
        Area current = area.getParent();
        while (current != null) {
            if (current.getType().equals(targetType.getType())) {
                return current;
            }
             if (current.getParent() == null || current.getParent().getId().equals(Area.ID_GLOBAL)) {
                break;
            }
            current = current.getParent();
        }
        return null; // 未找到
    }

}