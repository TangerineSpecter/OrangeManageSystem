package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户权限列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionListDto {

    private Long id;

    private String name;

    private String url;

    private String code;

}
