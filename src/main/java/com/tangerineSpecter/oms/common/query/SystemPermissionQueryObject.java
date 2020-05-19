package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 权限页面高级查询
 *
 * @author TangerineSpecter
 * @version 0.3.3
 * @date 2020年05月19日13:17:24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemPermissionQueryObject extends QueryObject {
    /**
     * 权限名称
     */
    private String name;
}
