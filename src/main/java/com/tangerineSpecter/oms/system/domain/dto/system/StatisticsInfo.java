package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.system.domain.enums.TradeIncomeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsInfo {

    /**
     * 当天收益
     */
    private BigDecimal todayIncome;
    /**
     * 本周收益
     */
    private BigDecimal weekendIncome;
    /**
     * 当月收益
     */
    private BigDecimal monthIncome;
    /**
     * 当年收益
     */
    private BigDecimal yearIncome;
    /**
     * 今天日期
     */
    private String today;
    /**
     * 本周
     */
    private Integer weekend;
    /**
     * 本月
     */
    private Integer month;
    /**
     * 本年
     */
    private Integer year;
    /**
     * 今日盈亏状态（0：盈利；1：亏损）
     */
    private Integer todayStatus;
    /**
     * 本周盈亏状态（0：盈利；1：亏损）
     */
    private Integer weekendStatus;
    /**
     * 本月盈亏状态（0：盈利；1：亏损）
     */
    private Integer monthStatus;
    /**
     * 本年盈亏状态（0：盈利；1：亏损）
     */
    private Integer yearStatus;
    /**
     * 最近30日资金曲线
     */
    private List<Integer> lastThirtyTotalMoney;
    /**
     * 最近30天日期
     */
    private List<String> lastThirtyDate;

    public void setTodayIncome(BigDecimal todayIncome) {
        this.todayIncome = todayIncome;
        this.todayStatus = TradeIncomeEnum.getIncomeStatus(todayIncome);
    }

    public void setWeekendIncome(BigDecimal weekendIncome) {
        this.weekendIncome = weekendIncome;
        this.weekendStatus = TradeIncomeEnum.getIncomeStatus(weekendIncome);
    }

    public void setMonthIncome(BigDecimal monthIncome) {
        this.monthIncome = monthIncome;
        this.monthStatus = TradeIncomeEnum.getIncomeStatus(monthIncome);
    }

    public void setYearIncome(BigDecimal yearIncome) {
        this.yearIncome = yearIncome;
        this.yearStatus = TradeIncomeEnum.getIncomeStatus(yearIncome);
    }
}
