package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统菜单表
 *
 * @author TangerineSpecter
 * @date 2019年09月01日02:27:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_menu")
public class SystemMenu implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 父级ID
     */
    private Long pid;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 跳转链接
     */
    private String href;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单等级
     */
    private Integer level;
    /**
     * 菜单跳转（_self:内部；_blank:外部）
     */
    private String target;
    /**
     * 菜单权限
     */
    private String permissionCode;
    /**
     * 菜单排序
     */
    private Integer sort;
}
