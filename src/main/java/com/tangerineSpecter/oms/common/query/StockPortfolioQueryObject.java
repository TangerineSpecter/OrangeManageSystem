package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 股票组合高级查询
 *
 * @author tangerineSpecter
 * @version 0.0.7
 * @date 2020年04月15日19:18:45
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockPortfolioQueryObject extends BaseQueryObject {

    private String keyword;
}
