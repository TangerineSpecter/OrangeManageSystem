package com.tangerinespecter.oms.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色信息参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemRoleInfoVo {

    private Long id;
    @NotBlank(message = "角色名称不能为空")
    private String name;
    /**
     * 权限Id列表
     */
    private List<Long> permissionIds;
}
