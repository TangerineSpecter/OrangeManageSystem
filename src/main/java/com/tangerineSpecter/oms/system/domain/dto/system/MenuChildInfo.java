package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuChildInfo {

    private Long id;
    /**
     * 子菜单标题
     */
    private String title;
    /**
     * 子菜单图标
     */
    private String icon;
    /**
     * 子菜单跳转地址
     */
    private String href;
    /**
     * 菜单类型
     */
    private Integer type;
    /**
     * 跳转方式（_self:自己；_blank:新窗口）
     */
    private String openType;
    /**
     * 权限code
     */
    private String permissionCode;

    private List<MenuChildInfo> children;
}
