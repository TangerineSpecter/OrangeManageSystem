package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 股票池页面高级查询
 *
 * @author TangerineSpecter
 * @version v0.0.7
 * @Date 2020年04月15日01:41:38
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockQueryObject extends QueryObject {
    /**
     * 类型（sh\sz\hk)
     */
    private String type;

    private String keyword;
}
