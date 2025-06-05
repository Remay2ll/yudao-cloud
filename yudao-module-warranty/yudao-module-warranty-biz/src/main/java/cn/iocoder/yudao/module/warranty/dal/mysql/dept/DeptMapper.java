package cn.iocoder.yudao.module.warranty.dal.mysql.dept;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.dept.DeptDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.dept.vo.*;

/**
 * 部门 Mapper
 *
 * @author Remay
 */
@Mapper
public interface DeptMapper extends BaseMapperX<DeptDO> {

    default PageResult<DeptDO> selectPage(DeptPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeptDO>()
                .likeIfPresent(DeptDO::getName, reqVO.getName())
                .eqIfPresent(DeptDO::getParentId, reqVO.getParentId())
                .eqIfPresent(DeptDO::getLeaderUserId, reqVO.getLeaderUserId())
                .eqIfPresent(DeptDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DeptDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeptDO::getId));
    }

}