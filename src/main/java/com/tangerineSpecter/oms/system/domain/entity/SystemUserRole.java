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
 * 系统用户角色关联表
 *
 * @author TangerineSpecter
 * @date 2020年04月20日18:14:39
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "system_user_role")
public class SystemUserRole {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色ID
     */
    private Long rid;
    /**
     * 用户ID
     */
    private String uid;
}
