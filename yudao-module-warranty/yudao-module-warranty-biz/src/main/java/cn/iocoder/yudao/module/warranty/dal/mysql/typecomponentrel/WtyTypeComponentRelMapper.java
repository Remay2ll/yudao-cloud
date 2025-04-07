package cn.iocoder.yudao.module.warranty.dal.mysql.typecomponentrel;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.typecomponentrel.vo.*;

/**
 * 设备类型-配件关联 Mapper
 *
 * @author Remay
 */
@Mapper
public interface WtyTypeComponentRelMapper extends BaseMapperX<WtyTypeComponentRelDO> {

    default PageResult<WtyTypeComponentRelDO> selectPage(WtyTypeComponentRelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WtyTypeComponentRelDO>()
                .eqIfPresent(WtyTypeComponentRelDO::getTypeId, reqVO.getTypeId())
                .eqIfPresent(WtyTypeComponentRelDO::getComponentId, reqVO.getComponentId())
                .eqIfPresent(WtyTypeComponentRelDO::getQuantity, reqVO.getQuantity())
                .betweenIfPresent(WtyTypeComponentRelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WtyTypeComponentRelDO::getId));
    }

    default PageResult<WtyTypeComponentRelDO> selectPage(PageParam reqVO, Long typeId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WtyTypeComponentRelDO>()
                .eq(WtyTypeComponentRelDO::getTypeId, typeId)
                .orderByDesc(WtyTypeComponentRelDO::getId));
    }

    default int deleteByTypeId(Long typeId) {
        return delete(WtyTypeComponentRelDO::getTypeId, typeId);
    }

}