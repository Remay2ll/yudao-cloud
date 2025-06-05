package cn.iocoder.yudao.module.warranty.dal.dataobject.dept;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 部门地址 DO
 *
 * @author Remay
 */
@TableName("warranty_dept_address")
@KeySequence("warranty_dept_address_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptAddressDO extends BaseDO {

    /**
     * 地址编号
     */
    @TableId
    private Long id;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 地址类型（例如：办公、仓库）
     */
    private String addressType;
    /**
     * 街道地址
     */
    private String streetAddress;
    /**
     * 城市
     */
    private String city;
    /**
     * 省份/州
     */
    private String stateProvince;
    /**
     * 邮政编码
     */
    private String postalCode;
    /**
     * 国家
     */
    private String country;
    /**
     * 是否默认地址
     */
    private Boolean isDefault;

}