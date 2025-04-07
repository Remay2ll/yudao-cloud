package cn.iocoder.yudao.module.warranty.service.typecomponentrel;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.typecomponentrel.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备类型-配件关联 Service 接口
 *
 * @author Remay
 */
public interface WtyTypeComponentRelService {

    /**
     * 创建设备类型-配件关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWtyTypeComponentRel(@Valid WtyTypeComponentRelSaveReqVO createReqVO);

    /**
     * 更新设备类型-配件关联
     *
     * @param updateReqVO 更新信息
     */
    void updateWtyTypeComponentRel(@Valid WtyTypeComponentRelSaveReqVO updateReqVO);

    /**
     * 删除设备类型-配件关联
     *
     * @param id 编号
     */
    void deleteWtyTypeComponentRel(Long id);

    /**
     * 获得设备类型-配件关联
     *
     * @param id 编号
     * @return 设备类型-配件关联
     */
    WtyTypeComponentRelDO getWtyTypeComponentRel(Long id);

    /**
     * 获得设备类型-配件关联分页
     *
     * @param pageReqVO 分页查询
     * @return 设备类型-配件关联分页
     */
    PageResult<WtyTypeComponentRelDO> getWtyTypeComponentRelPage(WtyTypeComponentRelPageReqVO pageReqVO);

}