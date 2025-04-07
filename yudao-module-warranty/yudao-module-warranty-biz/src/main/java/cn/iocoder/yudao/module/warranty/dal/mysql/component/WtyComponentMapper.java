package cn.iocoder.yudao.module.warranty.dal.mysql.component;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.component.WtyComponentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.component.vo.*;

/**
 * 配件 Mapper
 *
 * @author Remay
 */
@Mapper
public interface WtyComponentMapper extends BaseMapperX<WtyComponentDO> {

    default PageResult<WtyComponentDO> selectPage(WtyComponentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WtyComponentDO>()
                .likeIfPresent(WtyComponentDO::getComponentCode, reqVO.getComponentCode())
                .likeIfPresent(WtyComponentDO::getComponentName, reqVO.getComponentName())
                .eqIfPresent(WtyComponentDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WtyComponentDO::getIsAudit, reqVO.getIsAudit())
                .betweenIfPresent(WtyComponentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WtyComponentDO::getId));
    }

}