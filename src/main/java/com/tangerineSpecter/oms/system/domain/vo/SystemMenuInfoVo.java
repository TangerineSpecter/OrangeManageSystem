package com.tangerinespecter.oms.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemMenuInfoVo {
    @NotBlank(message = "菜单标题不能为空")
    private String title;
    @NotBlank(message = "跳转链接不能为空")
    private String href;

    private Long pid;

    private String icon;

    private Integer level;
    @NotBlank(message = "跳转方式不能为空")
    private String target;

    private Integer sort;
}
