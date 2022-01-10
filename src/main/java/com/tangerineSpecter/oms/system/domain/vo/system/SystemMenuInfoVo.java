package com.tangerinespecter.oms.system.domain.vo.system;

import com.tangerinespecter.oms.system.valid.IdParam;
import com.tangerinespecter.oms.system.valid.Insert;
import com.tangerinespecter.oms.system.valid.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemMenuInfoVo {

    @NotNull(message = "id不能为空", groups = {Update.class, IdParam.class})
    private Long id;
    @NotBlank(message = "菜单标题不能为空", groups = {Insert.class, Update.class})
    private String title;

    private String href;

    private Long pid;

    private String icon;

    private Integer level;
    @NotBlank(message = "跳转方式不能为空", groups = {Insert.class, Update.class})
    private String target;

    private Integer sort;
}
