package com.tangerinespecter.oms.system.domain.dto.statis;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 收益统计数据
 */
@Data
@Builder
@AllArgsConstructor
public class TradeStatisIncomeInfoDto {

    /**
     * 总收益
     */
    private List<BigDecimal> totalIncome;
    /**
     * 股票数据
     */
    private List<BigDecimal> stockData;
    /**
     * 期货数据
     */
    private List<BigDecimal> futuresData;
    /**
     * 外汇数据
     */
    private List<BigDecimal> foreignData;
    /**
     * 基金数据
     */
    private List<BigDecimal> fundsData;
    /**
     * 时间
     */
    private List<String> date;

    public TradeStatisIncomeInfoDto() {
        this.totalIncome = CollUtil.newArrayList();
        this.stockData = CollUtil.newArrayList();
        this.futuresData = CollUtil.newArrayList();
        this.foreignData = CollUtil.newArrayList();
        this.date = CollUtil.newArrayList();
    }
}
