package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 星座页面高级查询
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月9日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstellactionQueryObject extends QueryObject {
    /**
     * 查询星座
     */
    private String star;
    /**
     * 查询日期
     */
    private String queryDay;
}
