package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 权限模块表
 *
 * @author TangerineSpecter
 * @date 2020年02月15日20:39:22
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "system_permission_module")
public class SystemPermissionModule {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 权限模块名称
     */
    private String name;
    /**
     * 上层模块Id
     */
    private Long parentId;
    /**
     * 模块层级
     */
    private String level;
    /**
     * 状态（0：冻结；1：可用）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序（从大到小）
     */
    private Integer sort;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作时间
     */
    private String operateTime;
    /**
     * 操作ip
     */
    private Long operateIp;
}
