package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 交易逻辑页面高级查询
 *
 * @author TangerineSpecter
 * @version v0.4.1
 * @date 2020年06月05日11:49:50
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeLogicQueryObject extends BaseQueryObject {
    /**
     * 交易类型(0:股票；1：期货；2：外汇)
     */
    private Integer type;
    /**
     * 0：盈利；1：亏损
     */
    private Integer status;

}
