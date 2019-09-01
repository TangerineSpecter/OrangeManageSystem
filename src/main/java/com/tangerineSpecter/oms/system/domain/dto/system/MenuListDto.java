package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 菜单列表信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuListDto {

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
     * 权限
     */
    private String permission;
    /**
     * 图标
     */
    private String icon;
    /**
     * 菜单Url
     */
    private String href;
    /**
     * 序号
     */
    private Integer sort;
}
