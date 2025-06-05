package cn.iocoder.yudao.module.system.service.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.datapermission.core.annotation.DataPermission;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.mysql.dept.DeptMapper;
import cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;

/**
 * 部门 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public Long createDept(DeptSaveReqVO createReqVO) {
        if (createReqVO.getParentId() == null) {
            createReqVO.setParentId(DeptDO.PARENT_ID_ROOT);
        }
        // 校验父部门的有效性
        validateParentDept(null, createReqVO.getParentId());
        // 校验部门名的唯一性
        validateDeptNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入部门
        DeptDO dept = BeanUtils.toBean(createReqVO, DeptDO.class);
        deptMapper.insert(dept);
        return dept.getId();
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public void updateDept(DeptSaveReqVO updateReqVO) {
        if (updateReqVO.getParentId() == null) {
            updateReqVO.setParentId(DeptDO.PARENT_ID_ROOT);
        }
        // 校验自己存在
        validateDeptExists(updateReqVO.getId());
        // 校验父部门的有效性
        validateParentDept(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验部门名的唯一性
        validateDeptNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新部门
        DeptDO updateObj = BeanUtils.toBean(updateReqVO, DeptDO.class);
        deptMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public void deleteDept(Long id) {
        // 校验是否存在
        validateDeptExists(id);
        // 校验是否有子部门
        if (deptMapper.selectCountByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
        // 删除部门
        deptMapper.deleteById(id);
    }

    @VisibleForTesting
    void validateDeptExists(Long id) {
        if (id == null) {
            return;
        }
        DeptDO dept = deptMapper.selectById(id);
        if (dept == null) {
            throw exception(DEPT_NOT_FOUND);
        }
    }

    @VisibleForTesting
    void validateParentDept(Long id, Long parentId) {
        if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父部门
        if (Objects.equals(id, parentId)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 2. 父部门不存在
        DeptDO parentDept = deptMapper.selectById(parentId);
        if (parentDept == null) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父部门，如果父部门是自己的子部门，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentDept.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(DEPT_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父部门
            if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentDept = deptMapper.selectById(parentId);
            if (parentDept == null) {
                break;
            }
        }
    }

    @VisibleForTesting
    void validateDeptNameUnique(Long id, Long parentId, String name) {
        DeptDO dept = deptMapper.selectByParentIdAndName(parentId, name);
        if (dept == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的部门
        if (id == null) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dept.getId(), id)) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
    }

    @Override
    public DeptDO getDept(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<DeptDO> getDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return deptMapper.selectBatchIds(ids);
    }

    @Override
    public List<DeptDO> getDeptList(DeptListReqVO reqVO) {
        List<DeptDO> list = deptMapper.selectList(reqVO);
        list.sort(Comparator.comparing(DeptDO::getSort));
        return list;
    }

    @Override
    public List<DeptDO> getChildDeptList(Collection<Long> ids) {
        List<DeptDO> children = new LinkedList<>();
        // 遍历每一层
        Collection<Long> parentIds = ids;
        for (int i = 0; i < Short.MAX_VALUE; i++) { // 使用 Short.MAX_VALUE 避免 bug 场景下，存在死循环
            // 查询当前层，所有的子部门
            List<DeptDO> depts = deptMapper.selectListByParentId(parentIds);
            // 1. 如果没有子部门，则结束遍历
            if (CollUtil.isEmpty(depts)) {
                break;
            }
            // 2. 如果有子部门，继续遍历
            children.addAll(depts);
            parentIds = convertSet(depts, DeptDO::getId);
        }
        return children;
    }

    @Override
    public List<DeptDO> getDeptListByLeaderUserId(Long id) {
        return deptMapper.selectListByLeaderUserId(id);
    }

    @Override
    @DataPermission(enable = false) // 禁用数据权限，避免建立不正确的缓存
    @Cacheable(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST, key = "#id")
    public Set<Long> getChildDeptIdListFromCache(Long id) {
        List<DeptDO> children = getChildDeptList(id);
        return convertSet(children, DeptDO::getId);
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        Map<Long, DeptDO> deptMap = getDeptMap(ids);
        // 校验
        ids.forEach(id -> {
            DeptDO dept = deptMap.get(id);
            if (dept == null) {
                throw exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
                throw exception(DEPT_NOT_ENABLE, dept.getName());
            }
        });
    }

    @Override
    public List<DeptDO> getMyAccessibleDeptList(Long userId) {
        // 1. 获取用户基本信息，从而获得用户所在的部门 ID
        AdminUserDO user = adminUserMapper.selectById(userId);
        if (user == null || user.getDeptId() == null) {
            return Collections.emptyList(); // 或者根据业务需求抛出异常
        }
        Long userDeptId = user.getDeptId();

        // 2. 获取用户所在部门及其所有下级部门
        List<DeptDO> accessibleDepts = new LinkedList<>();
        DeptDO userDept = deptMapper.selectById(userDeptId);
        if (userDept != null) {
            accessibleDepts.add(userDept); // 添加用户自己的部门
            // 获取所有子部门
            List<DeptDO> childrenDepts = getChildDeptList(Collections.singletonList(userDeptId)); // 使用 getChildDeptList
            accessibleDepts.addAll(childrenDepts);
        }

        // 3. 过滤，只保留状态正常且类型为网点（假设类型 1 为网点）的部门
        // 注意: "类型为网点" 的判断条件需要根据您的实际 DeptDO 定义来调整
        // 例如，如果 DeptDO 有一个 type 字段，可以这样过滤：
        // .filter(dept -> CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus()) && Integer.valueOf(1).equals(dept.getType()))
        // 这里暂时只按下线状态过滤
        return accessibleDepts.stream()
                .filter(dept -> CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus()))
                // TODO: 根据实际情况添加网点类型的过滤条件 (例如 dept.getType().equals(YOUR_NET_POINT_TYPE_ENUM))
                .sorted(Comparator.comparing(DeptDO::getSort)) // 按排序字段排序
                .distinct() //确保部门不重复
                .toList();
    }

    @Override
    @PostConstruct
    public void initLocalCache() {
        // TODO 未来实现缓存初始化逻辑，例如：
        // 1. 加载所有部门数据到本地缓存
        // 2. 构建部门树形结构缓存
        // 3. 缓存部门层级关系等
        log.info("[initLocalCache] 初始化部门本地缓存...");
    }

    @Override
    public List<DeptDO> getDeptWithChildrenFiltered(Long id, Integer status, Integer type) {
        List<DeptDO> resultList = new ArrayList<>();
        DeptDO rootDept = deptMapper.selectById(id);

        // 检查根部门是否符合条件
        if (rootDept != null) {
            boolean statusMatch = (status == null) || status.equals(rootDept.getStatus());
            boolean typeMatch = (type == null) || type.equals(rootDept.getType());
            if (statusMatch && typeMatch) {
                resultList.add(rootDept);
            }
            // 递归获取并过滤子部门
            collectChildrenFiltered(rootDept.getId(), status, type, resultList);
        }
        return resultList.stream().distinct().sorted(Comparator.comparing(DeptDO::getSort)).toList(); //去重和排序
    }

    private void collectChildrenFiltered(Long parentId, Integer status, Integer type, List<DeptDO> resultList) {
        // 使用 LambdaQueryWrapperX 进行条件查询
        LambdaQueryWrapperX<DeptDO> queryWrapper = new LambdaQueryWrapperX<DeptDO>()
                .eq(DeptDO::getParentId, parentId)
                .eqIfPresent(DeptDO::getStatus, status)
                .eqIfPresent(DeptDO::getType, type);
        
        List<DeptDO> children = deptMapper.selectList(queryWrapper);

        if (CollUtil.isNotEmpty(children)) {
            resultList.addAll(children);
            for (DeptDO child : children) {
                collectChildrenFiltered(child.getId(), status, type, resultList);
            }
        }
    }

}
