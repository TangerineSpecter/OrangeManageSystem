package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 系统角色高级查询
 *
 * @author tangerineSpecter
 * @version 0.0.8
 * @date 2020年04月20日13:50:50
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemRoleQueryObject extends QueryObject {

    private Integer type;

    private String name;
}
