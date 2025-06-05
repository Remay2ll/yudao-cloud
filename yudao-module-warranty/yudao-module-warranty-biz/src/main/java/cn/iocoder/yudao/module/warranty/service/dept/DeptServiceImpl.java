package cn.iocoder.yudao.module.warranty.service.dept;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.ip.core.utils.AreaUtils;
import cn.iocoder.yudao.framework.ip.core.Area;
import cn.iocoder.yudao.framework.ip.core.enums.AreaTypeEnum;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.dept.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.deptaddress.DeptAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.dept.DeptMapper;
import cn.iocoder.yudao.module.warranty.dal.mysql.deptaddress.DeptAddressMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 部门 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;
    @Resource
    private DeptAddressMapper deptAddressMapper;

    private void validateDeptExists(Long id) {
        if (deptMapper.selectById(id) == null) {
            throw exception(DEPT_NOT_EXISTS);
        }
    }

    @Override
    public DeptDO getDept(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public PageResult<DeptDO> getDeptPage(DeptPageReqVO pageReqVO) {
        return deptMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（部门地址） ====================

    @Override
    public PageResult<DeptAddressDO> getDeptAddressPage(PageParam pageReqVO, Long deptId) {
        return deptAddressMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<DeptAddressDO>()
                .eq(DeptAddressDO::getDeptId, deptId));
    }

    @Override
    @Transactional
    public Long createDeptAddress(DeptAddressDO deptAddress) {
        populateAddressFieldsFromAreaId(deptAddress, deptAddress.getAreaId());

        if (Boolean.TRUE.equals(deptAddress.getIsDefault())) {
            deptAddressMapper.updateNonDefaultByDeptId(deptAddress.getDeptId());
        }

        deptAddressMapper.insert(deptAddress);
        return deptAddress.getId();
    }

    @Override
    @Transactional
    public void updateDeptAddress(DeptAddressDO deptAddress) {
        validateDeptAddressExists(deptAddress.getId());

        populateAddressFieldsFromAreaId(deptAddress, deptAddress.getAreaId());

        if (Boolean.TRUE.equals(deptAddress.getIsDefault())) {
            deptAddressMapper.updateNonDefaultByDeptIdAndExcludeId(deptAddress.getDeptId(), deptAddress.getId());
        }

        deptAddressMapper.updateById(deptAddress);
    }

    @Override
    public void deleteDeptAddress(Long id) {
        validateDeptAddressExists(id);
        deptAddressMapper.deleteById(id);
    }

    @Override
    public DeptAddressDO getDeptAddress(Long id) {
        return deptAddressMapper.selectById(id);
    }

    @Override
    public List<DeptAddressDO> getDeptAddressByDeptIdAndDefault(Long deptId, Boolean isDefault) {
        return deptAddressMapper.selectByDeptIdAndDefault(deptId, isDefault);
    }

    private void validateDeptAddressExists(Long id) {
        if (deptAddressMapper.selectById(id) == null) {
            throw exception(DEPT_ADDRESS_NOT_EXISTS);
        }
    }

    private void deleteDeptAddressByDeptId(Long deptId) {
        deptAddressMapper.deleteByDeptId(deptId);
    }

    private void populateAddressFieldsFromAreaId(DeptAddressDO deptAddress, Long areaId) {
        if (areaId == null) {
            deptAddress.setCountry(null);
            deptAddress.setStateProvince(null);
            deptAddress.setCity(null);
            deptAddress.setDistrict(null);
            return;
        }
        Area area = AreaUtils.getArea(areaId.intValue());
        if (area == null) {
            deptAddress.setCountry(null);
            deptAddress.setStateProvince(null);
            deptAddress.setCity(null);
            deptAddress.setDistrict(null);
            return;
        }
        deptAddress.setCountry(null);
        deptAddress.setStateProvince(null);
        deptAddress.setCity(null);
        deptAddress.setDistrict(null);

        Area current = area;

        Area countryNode = findParentByType(current, AreaTypeEnum.COUNTRY);
        if (countryNode != null) {
            deptAddress.setCountry(countryNode.getName());
        } else {
            if (current.getId() != null && current.getId() > 0 && current.getId() < 1000000) { 
                 deptAddress.setCountry("中国");
            }
        }

        Area provinceNode = findParentOrSelfByType(current, AreaTypeEnum.PROVINCE);
        if (provinceNode != null) {
            deptAddress.setStateProvince(provinceNode.getName());
        }

        Area cityNode = findParentOrSelfByType(current, AreaTypeEnum.CITY);
        if (cityNode != null) {
            if (provinceNode == null || !provinceNode.getId().equals(cityNode.getId())) {
                 deptAddress.setCity(cityNode.getName());
            }
            if (cityNode.getType().equals(AreaTypeEnum.PROVINCE.getType()) && area.getType().equals(AreaTypeEnum.DISTRICT.getType())) {
                Area actualCityForDistrict = area.getParent();
                if (actualCityForDistrict != null && actualCityForDistrict.getType().equals(AreaTypeEnum.CITY.getType())) {
                    deptAddress.setCity(actualCityForDistrict.getName());
                }
            }
        }

        if (area.getType().equals(AreaTypeEnum.DISTRICT.getType())) {
            deptAddress.setDistrict(area.getName());
            if (deptAddress.getCity() == null && area.getParent() != null && area.getParent().getType().equals(AreaTypeEnum.CITY.getType())) {
                deptAddress.setCity(area.getParent().getName());
                if (deptAddress.getStateProvince() == null && area.getParent().getParent() != null && area.getParent().getParent().getType().equals(AreaTypeEnum.PROVINCE.getType())){
                    deptAddress.setStateProvince(area.getParent().getParent().getName());
                }
            }
             if (deptAddress.getStateProvince() == null && area.getParent() != null && area.getParent().getType().equals(AreaTypeEnum.PROVINCE.getType())) {
                deptAddress.setStateProvince(area.getParent().getName());
             }
        }
        
        if (deptAddress.getStateProvince() != null && deptAddress.getStateProvince().equals(deptAddress.getCity())) {
            if (deptAddress.getDistrict() != null) {
            }
        }
    }

    private Area findParentOrSelfByType(Area area, AreaTypeEnum targetType) {
        Area current = area;
        while (current != null) {
            if (current.getType().equals(targetType.getType())) {
                return current;
            }
            if (current.getParent() == null || current.getParent().getId().equals(Area.ID_GLOBAL)) {
                break;
            }
            current = current.getParent();
        }
        return null;
    }
    
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
        return null;
    }

}