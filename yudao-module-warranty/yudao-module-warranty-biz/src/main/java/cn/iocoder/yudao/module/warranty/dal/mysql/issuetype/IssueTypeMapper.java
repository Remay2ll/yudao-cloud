package cn.iocoder.yudao.module.warranty.dal.mysql.issuetype;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.issuetype.IssueTypeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.issuetype.vo.*;

/**
 * 故障类型 Mapper
 *
 * @author Remay
 */
@Mapper
public interface IssueTypeMapper extends BaseMapperX<IssueTypeDO> {

    default List<IssueTypeDO> selectList(IssueTypeListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<IssueTypeDO>()
                .eqIfPresent(IssueTypeDO::getTypeCode, reqVO.getTypeCode())
                .likeIfPresent(IssueTypeDO::getTypeName, reqVO.getTypeName())
                .eqIfPresent(IssueTypeDO::getPriorityLevel, reqVO.getPriorityLevel())
                .eqIfPresent(IssueTypeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IssueTypeDO::getIsAudit, reqVO.getIsAudit())
                .betweenIfPresent(IssueTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IssueTypeDO::getId));
    }

	default IssueTypeDO selectByParentIdAndTypeName(Long parentId, String typeName) {
	    return selectOne(IssueTypeDO::getParentId, parentId, IssueTypeDO::getTypeName, typeName);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(IssueTypeDO::getParentId, parentId);
    }

}