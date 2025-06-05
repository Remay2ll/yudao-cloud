package cn.iocoder.yudao.module.warranty.service.component;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.component.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.component.WtyComponentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 配件 Service 接口
 *
 * @author Remay
 */
public interface WtyComponentService {

    /**
     * 创建配件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWtyComponent(@Valid WtyComponentSaveReqVO createReqVO);

    /**
     * 更新配件
     *
     * @param updateReqVO 更新信息
     */
    void updateWtyComponent(@Valid WtyComponentSaveReqVO updateReqVO);

    /**
     * 删除配件
     *
     * @param id 编号
     */
    void deleteWtyComponent(Long id);

    /**
     * 获得配件
     *
     * @param id 编号
     * @return 配件
     */
    WtyComponentDO getWtyComponent(Long id);

    /**
     * 获得配件分页
     *
     * @param pageReqVO 分页查询
     * @return 配件分页
     */
    PageResult<WtyComponentDO> getWtyComponentPage(WtyComponentPageReqVO pageReqVO);

    /**
     * 获得配件列表
     *
     *
     * @return 配件列表
     */
    List<WtyComponentDO> getWtyComponentList();
}