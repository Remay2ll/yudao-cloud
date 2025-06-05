package cn.iocoder.yudao.module.warranty.service.maintenorderpart;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.maintenorderpart.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.maintenorderpart.MaintenOrderPartDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 工单配件 Service 接口
 *
 * @author Remay
 */
public interface MaintenOrderPartService {

    /**
     * 创建工单配件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaintenOrderPart(@Valid MaintenOrderPartSaveReqVO createReqVO);

    /**
     * 更新工单配件
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintenOrderPart(@Valid MaintenOrderPartSaveReqVO updateReqVO);

    /**
     * 删除工单配件
     *
     * @param id 编号
     */
    void deleteMaintenOrderPart(Long id);

    /**
     * 获得工单配件
     *
     * @param id 编号
     * @return 工单配件
     */
    MaintenOrderPartDO getMaintenOrderPart(Long id);

    /**
     * 获得工单配件分页
     *
     * @param pageReqVO 分页查询
     * @return 工单配件分页
     */
    PageResult<MaintenOrderPartDO> getMaintenOrderPartPage(MaintenOrderPartPageReqVO pageReqVO);

}