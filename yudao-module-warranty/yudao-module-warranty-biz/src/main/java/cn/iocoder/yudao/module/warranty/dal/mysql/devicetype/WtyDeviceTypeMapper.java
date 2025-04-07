package cn.iocoder.yudao.module.warranty.dal.mysql.devicetype;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype.WtyDeviceTypeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo.*;

/**
 * 设备类型 Mapper
 *
 * @author Remay
 */
@Mapper
public interface WtyDeviceTypeMapper extends BaseMapperX<WtyDeviceTypeDO> {

    default PageResult<WtyDeviceTypeDO> selectPage(WtyDeviceTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WtyDeviceTypeDO>()
                .likeIfPresent(WtyDeviceTypeDO::getTypeCode, reqVO.getTypeCode())
                .likeIfPresent(WtyDeviceTypeDO::getTypeName, reqVO.getTypeName())
                .eqIfPresent(WtyDeviceTypeDO::getParentTypeId, reqVO.getParentTypeId())
                .eqIfPresent(WtyDeviceTypeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WtyDeviceTypeDO::getIsAudit, reqVO.getIsAudit())
                .betweenIfPresent(WtyDeviceTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WtyDeviceTypeDO::getId));
    }

}