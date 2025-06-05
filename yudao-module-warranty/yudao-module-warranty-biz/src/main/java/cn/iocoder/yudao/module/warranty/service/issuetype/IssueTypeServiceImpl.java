package cn.iocoder.yudao.module.warranty.service.issuetype;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.warranty.controller.admin.issuetype.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.issuetype.IssueTypeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.warranty.dal.mysql.issuetype.IssueTypeMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.warranty.enums.ErrorCodeConstants.*;

/**
 * 故障类型 Service 实现类
 *
 * @author Remay
 */
@Service
@Validated
public class IssueTypeServiceImpl implements IssueTypeService {

    @Resource
    private IssueTypeMapper issueTypeMapper;

    @Override
    public Long createIssueType(IssueTypeSaveReqVO createReqVO) {
        // 校验父级故障类型ID（0表示顶级分类）的有效性
        validateParentIssueType(null, createReqVO.getParentId());
        // 校验故障类型名称的唯一性
        validateIssueTypeTypeNameUnique(null, createReqVO.getParentId(), createReqVO.getTypeName());

        // 插入
        IssueTypeDO issueType = BeanUtils.toBean(createReqVO, IssueTypeDO.class);
        issueTypeMapper.insert(issueType);
        // 返回
        return issueType.getId();
    }

    @Override
    public void updateIssueType(IssueTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateIssueTypeExists(updateReqVO.getId());
        // 校验父级故障类型ID（0表示顶级分类）的有效性
        validateParentIssueType(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验故障类型名称的唯一性
        validateIssueTypeTypeNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getTypeName());

        // 更新
        IssueTypeDO updateObj = BeanUtils.toBean(updateReqVO, IssueTypeDO.class);
        issueTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteIssueType(Long id) {
        // 校验存在
        validateIssueTypeExists(id);
        // 校验是否有子故障类型
        if (issueTypeMapper.selectCountByParentId(id) > 0) {
            throw exception(ISSUE_TYPE_EXITS_CHILDREN);
        }
        // 删除
        issueTypeMapper.deleteById(id);
    }

    private void validateIssueTypeExists(Long id) {
        if (issueTypeMapper.selectById(id) == null) {
            throw exception(ISSUE_TYPE_NOT_EXISTS);
        }
    }

    private void validateParentIssueType(Long id, Long parentId) {
        if (parentId == null || IssueTypeDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父故障类型
        if (Objects.equals(id, parentId)) {
            throw exception(ISSUE_TYPE_PARENT_ERROR);
        }
        // 2. 父故障类型不存在
        IssueTypeDO parentIssueType = issueTypeMapper.selectById(parentId);
        if (parentIssueType == null) {
            throw exception(ISSUE_TYPE_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父故障类型，如果父故障类型是自己的子故障类型，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentIssueType.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(ISSUE_TYPE_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父故障类型
            if (parentId == null || IssueTypeDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentIssueType = issueTypeMapper.selectById(parentId);
            if (parentIssueType == null) {
                break;
            }
        }
    }

    private void validateIssueTypeTypeNameUnique(Long id, Long parentId, String typeName) {
        IssueTypeDO issueType = issueTypeMapper.selectByParentIdAndTypeName(parentId, typeName);
        if (issueType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的故障类型
        if (id == null) {
            throw exception(ISSUE_TYPE_TYPE_NAME_DUPLICATE);
        }
        if (!Objects.equals(issueType.getId(), id)) {
            throw exception(ISSUE_TYPE_TYPE_NAME_DUPLICATE);
        }
    }

    @Override
    public IssueTypeDO getIssueType(Long id) {
        return issueTypeMapper.selectById(id);
    }

    @Override
    public List<IssueTypeDO> getIssueTypeList(IssueTypeListReqVO listReqVO) {
        return issueTypeMapper.selectList(listReqVO);
    }

    @Override
    public List<IssueTypeSimpleTreeRespVO> getIssueTypeSimpleTree() {
        // 获取所有启用状态的故障类型
        List<IssueTypeDO> allIssueTypes = issueTypeMapper.selectList(new IssueTypeListReqVO());
        
        // 过滤出启用状态的故障类型
        List<IssueTypeDO> enabledIssueTypes = allIssueTypes.stream()
                .filter(issueType -> Boolean.TRUE.equals(issueType.getStatus()))
                .toList();
        
        // 转换为精简树结构VO
        List<IssueTypeSimpleTreeRespVO> treeList = BeanUtils.toBean(enabledIssueTypes, IssueTypeSimpleTreeRespVO.class);
        
        // 构建树结构
        return buildTree(treeList);
    }

    /**
     * 构建树结构
     *
     * @param list 故障类型列表
     * @return 树结构列表
     */
    private List<IssueTypeSimpleTreeRespVO> buildTree(List<IssueTypeSimpleTreeRespVO> list) {
        // 创建ID到节点的映射
        Map<Long, IssueTypeSimpleTreeRespVO> nodeMap = new HashMap<>();
        for (IssueTypeSimpleTreeRespVO node : list) {
            nodeMap.put(node.getId(), node);
        }
        
        // 构建树结构
        List<IssueTypeSimpleTreeRespVO> rootNodes = new ArrayList<>();
        for (IssueTypeSimpleTreeRespVO node : list) {
            if (node.getParentId() == null || IssueTypeDO.PARENT_ID_ROOT.equals(node.getParentId())) {
                // 根节点
                rootNodes.add(node);
            } else {
                // 子节点，添加到父节点的children中
                IssueTypeSimpleTreeRespVO parent = nodeMap.get(node.getParentId());
                if (parent != null) {
                    parent.addChild(node);
                }
            }
        }
        
        return rootNodes;
    }

}