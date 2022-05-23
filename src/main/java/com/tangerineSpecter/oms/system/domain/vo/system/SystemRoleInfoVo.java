package com.tangerinespecter.oms.system.domain.vo.system;

import com.tangerinespecter.oms.system.valid.Delete;
import com.tangerinespecter.oms.system.valid.Insert;
import com.tangerinespecter.oms.system.valid.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色信息参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemRoleInfoVo implements Serializable {

    @NotNull(message = "id不能为空", groups = {Update.class, Delete.class})
    private Long id;
    @NotBlank(message = "角色名称不能为空", groups = {Insert.class})
    private String name;
    /**
     * 权限Id列表
     */
    private String permissionIds;
}
