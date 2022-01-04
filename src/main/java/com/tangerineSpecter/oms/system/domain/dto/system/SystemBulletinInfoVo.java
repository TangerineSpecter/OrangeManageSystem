package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.system.valid.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemBulletinInfoVo {

    @NotNull(message = "id不能为空", groups = {Update.class})
    private Long id;
    @NotBlank(message = "公告标题不能为空")
    private String title;
    @NotBlank(message = "公告内容不能为空")
    private String content;

}
