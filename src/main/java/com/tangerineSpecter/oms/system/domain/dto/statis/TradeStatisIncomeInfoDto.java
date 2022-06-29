package com.tangerinespecter.oms.system.domain.dto.statis;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
     * 股票累计收益
     */
    private List<BigDecimal> stockTotalIncome;
    /**
     * 期货数据
     */
    private List<BigDecimal> futuresData;
    /**
     * 期货累计收益
     */
    private List<BigDecimal> futuresTotalIncome;
    /**
     * 外汇数据
     */
    private List<BigDecimal> foreignData;
    /**
     * 外汇累计收益
     */
    private List<BigDecimal> foreignTotalIncome;
    /**
     * 基金数据
     */
    private List<BigDecimal> fundsData;
    /**
     * 基金累计收益
     */
    private List<BigDecimal> fundsTotalIncome;
    /**
     * 时间
     */
    private List<String> date;

    public TradeStatisIncomeInfoDto() {
        this.totalIncome = CollUtil.newArrayList();
        this.stockData = CollUtil.newArrayList();
        this.futuresData = CollUtil.newArrayList();
        this.foreignData = CollUtil.newArrayList();
        this.fundsData = CollUtil.newArrayList();
        this.stockTotalIncome = CollUtil.newArrayList();
        this.futuresTotalIncome = CollUtil.newArrayList();
        this.foreignTotalIncome = CollUtil.newArrayList();
        this.fundsTotalIncome = CollUtil.newArrayList();
        this.date = CollUtil.newArrayList();
    }

    /**
     * 设置交易数据
     *
     * @param stockData   股票数据
     * @param futuresData 期货数据
     * @param foreignData 外汇数据
     * @param fundsData   基金数据
     */
    public void setTradeData(BigDecimal stockData, BigDecimal futuresData, BigDecimal foreignData, BigDecimal fundsData) {
        this.stockData.add(stockData);
        this.futuresData.add(futuresData);
        this.foreignData.add(foreignData);
        this.fundsData.add(fundsData);
        this.totalIncome.add(stockData.add(futuresData).add(foreignData).add(fundsData).setScale(0, RoundingMode.CEILING));
    }

    /**
     * 设置累计收益
     *
     * @param stockTotalIncome   股票累计数据
     * @param futuresTotalIncome 期货累计数据
     * @param foreignTotalIncome 外汇累计数据
     * @param fundsTotalIncome   基金累计数据
     */
    public void setTradeTotalIncome(BigDecimal stockTotalIncome, BigDecimal futuresTotalIncome, BigDecimal foreignTotalIncome, BigDecimal fundsTotalIncome) {
        this.stockTotalIncome.add(stockTotalIncome);
        this.futuresTotalIncome.add(futuresTotalIncome);
        this.foreignTotalIncome.add(foreignTotalIncome);
        this.fundsTotalIncome.add(fundsTotalIncome);
    }
}
