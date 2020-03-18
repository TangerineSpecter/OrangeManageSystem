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
 * 系统权限更新记录表
 *
 * @author TangerineSpecter
 * @date 2020年02月15日20:41:35
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "system_permission_log")
public class SystemPermissionLog {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 权限更新类型（0：部门；1：用户；2：权限模块；3：权限；4：角色；5：角色用户关系；6：角色权限关系）
     */
    private Integer type;
    /**
     * 基于type指定的对象id，比如用户、权限、角色表的主键
     */
    private Long targetId;
    /**
     * 旧数据
     */
    private String oldValue;
    /**
     * 新数据
     */
    private String newValue;
    /**
     * 状态（0：冻结；1：可用）
     */
    private Integer status;
}
