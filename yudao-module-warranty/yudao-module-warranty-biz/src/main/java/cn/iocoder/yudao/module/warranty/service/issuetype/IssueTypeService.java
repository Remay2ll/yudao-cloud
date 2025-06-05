package cn.iocoder.yudao.module.warranty.service.issuetype;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.warranty.controller.admin.issuetype.vo.*;
import cn.iocoder.yudao.module.warranty.dal.dataobject.issuetype.IssueTypeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 故障类型 Service 接口
 *
 * @author Remay
 */
public interface IssueTypeService {

    /**
     * 创建故障类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createIssueType(@Valid IssueTypeSaveReqVO createReqVO);

    /**
     * 更新故障类型
     *
     * @param updateReqVO 更新信息
     */
    void updateIssueType(@Valid IssueTypeSaveReqVO updateReqVO);

    /**
     * 删除故障类型
     *
     * @param id 编号
     */
    void deleteIssueType(Long id);

    /**
     * 获得故障类型
     *
     * @param id 编号
     * @return 故障类型
     */
    IssueTypeDO getIssueType(Long id);

    /**
     * 获得故障类型列表
     *
     * @param listReqVO 查询条件
     * @return 故障类型列表
     */
    List<IssueTypeDO> getIssueTypeList(IssueTypeListReqVO listReqVO);

    /**
     * 获得故障类型精简树结构
     *
     * @return 故障类型精简树结构列表
     */
    List<IssueTypeSimpleTreeRespVO> getIssueTypeSimpleTree();

}