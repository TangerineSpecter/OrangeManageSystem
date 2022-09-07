package com.tangerinespecter.oms.system.domain.dto.system;

import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeIncomeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 初始化统计时间
     *
     * @param lastDate 最后结算日
     */
    public void initDate(String lastDate) {
        Date currentDate = new Date();
        this.year = DateUtil.year(currentDate);
        this.month = DateUtil.month(currentDate) + 1;
        this.weekend = DateUtil.weekOfYear(currentDate);
        this.today = lastDate;
    }

}
