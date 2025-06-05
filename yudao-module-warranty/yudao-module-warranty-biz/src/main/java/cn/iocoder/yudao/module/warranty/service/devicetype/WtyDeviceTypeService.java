package cn.iocoder.yudao.module.warranty.service.devicetype;

import java.util.*;

import cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype.WtyDeviceTypeWithPNameDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDoWithCompName;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.devicetype.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.devicetype.WtyDeviceTypeDO;
import cn.iocoder.yudao.module.warranty.dal.dataobject.typecomponentrel.WtyTypeComponentRelDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备类型 Service 接口
 *
 * @author Remay
 */
public interface WtyDeviceTypeService {

    /**
     * 创建设备类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWtyDeviceType(@Valid WtyDeviceTypeSaveReqVO createReqVO);

    /**
     * 更新设备类型
     *
     * @param updateReqVO 更新信息
     */
    void updateWtyDeviceType(@Valid WtyDeviceTypeSaveReqVO updateReqVO);

    /**
     * 删除设备类型
     *
     * @param id 编号
     */
    void deleteWtyDeviceType(Long id);

    /**
     * 获得设备类型
     *
     * @param id 编号
     * @return 设备类型
     */
    WtyDeviceTypeDO getWtyDeviceType(Long id);

    /**
     * 获得设备类型列表
     *
     * @param ids 编号列表
     * @return 设备类型列表
     */
    List<WtyDeviceTypeDO> getWtyDeviceTypeList(Collection<Long> ids);

    /**
     * 获得设备类型分页
     *
     * @param pageReqVO 分页查询
     * @return 设备类型分页
     */
    PageResult<WtyDeviceTypeDO> getWtyDeviceTypePage(WtyDeviceTypePageReqVO pageReqVO);

    /**
     * 获得设备类型分页（带父节点）
     *
     * @param pageReqVO 分页查询
     * @return 设备类型分页
     */
    PageResult<WtyDeviceTypeWithPNameDO> getWtyDeviceTypePageWithP(WtyDeviceTypePageReqVO pageReqVO);

    // ==================== 子表（设备类型-配件关联） ====================

    /**
     * 获得设备类型-配件关联分页
     *
     * @param pageReqVO 分页查询
     * @param typeId 设备类型ID
     * @return 设备类型-配件关联分页
     */
    PageResult<WtyTypeComponentRelDO> getWtyTypeComponentRelPage(PageParam pageReqVO, Long typeId);

    /**
     * 获得包含配件名称的设备类型-配件关联分页
     *
     * @param pageReqVO 分页查询
     * @param typeId 设备类型ID
     * @return 设备类型-配件关联分页
     */
    PageResult<WtyTypeComponentRelDoWithCompName> getWtyTypeComponentRelPageWithCompName(PageParam pageReqVO, Long typeId);

    /**
     * 创建设备类型-配件关联
     *
     * @param wtyTypeComponentRel 创建信息
     * @return 编号
     */
    Long createWtyTypeComponentRel(@Valid WtyTypeComponentRelDO wtyTypeComponentRel);

    /**
     * 更新设备类型-配件关联
     *
     * @param wtyTypeComponentRel 更新信息
     */
    void updateWtyTypeComponentRel(@Valid WtyTypeComponentRelDO wtyTypeComponentRel);

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
     * 获得设备类型
     *
     * @param reqVO 父节点信息
     * @return 设备类型列表
     */
    List<WtyDeviceTypeDO> getWtyDeviceTypeTree(WtyDeviceTypeTreeReqVO reqVO);

    /**
     * 获得单层设备类型树
     *
     * @param reqVO 父节点信息
     * @return 设备类型列表
     */
    List<WtyDeviceTypeDO> getWtyDeviceTypeTreeOneLevel(WtyDeviceTypeTreeReqVO reqVO);
}