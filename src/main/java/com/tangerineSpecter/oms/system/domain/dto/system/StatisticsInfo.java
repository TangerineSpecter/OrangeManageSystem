package com.tangerinespecter.oms.system.domain.dto.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 初始化数据
     *
     * @param tradeRecordMap key：时间；value：时间对应收益总和
     */
    public StatisticsInfo(Map<String, Integer> tradeRecordMap) {
        Date currentDate = new Date();
        this.year = DateUtil.year(currentDate);
        this.month = DateUtil.month(currentDate) + 1;
        this.weekend = DateUtil.weekOfYear(currentDate);
        this.today = CollUtil.getFirst(tradeRecordMap.keySet());
        this.lastThirtyTotalMoney = CollUtil.newArrayList();
        this.calIncome(tradeRecordMap);
    }

    /**
     * 收益计算
     *
     * @param totalIncomeMap key：时间；value：收益
     */
    private void calIncome(Map<String, Integer> totalIncomeMap) {
        CollUtils.forEach(totalIncomeMap.keySet(), key -> {
            Integer income = totalIncomeMap.get(key);
            long date = DateUtil.parse(key, DateFormat.getDateInstance()).getTime();
            this.yearIncome = calYearIncome(date, income);
            this.monthIncome = calMonthIncome(date, income);
            this.weekendIncome = calWeekendIncome(date, income);
            this.todayIncome = calTodayIncome(key, income);
        });
    }

    /**
     * 计算今年累计收益
     *
     * @param currentTime 当前时间戳
     * @param income      时间对应的收益
     * @return 收益结果
     */
    private BigDecimal calYearIncome(long currentTime, Integer income) {
        long thisYear = DateUtil.beginOfYear(new Date()).getTime();
        if (currentTime >= thisYear) {
            return NumChainCal.startOf(this.yearIncome).add(income).getBigDecimal();
        }
        return this.yearIncome;
    }

    /**
     * 计算本月累计收益
     *
     * @param currentTime 当前时间戳
     * @param income      时间对应的收益
     * @return 收益结果
     */
    private BigDecimal calMonthIncome(long currentTime, Integer income) {
        long thisMonth = DateUtil.beginOfMonth(new Date()).getTime();
        if (currentTime >= thisMonth) {
            return NumChainCal.startOf(this.monthIncome).add(income).getBigDecimal();
        }
        return this.monthIncome;
    }

    /**
     * 计算本周累计收益
     *
     * @param currentTime 当前时间戳
     * @param income      时间对应的收益
     * @return 收益结果
     */
    private BigDecimal calWeekendIncome(long currentTime, Integer income) {
        long thisWeek = DateUtil.beginOfWeek(new Date()).getTime();
        if (currentTime >= thisWeek) {
            return NumChainCal.startOf(this.weekendIncome).add(income).getBigDecimal();
        }
        return this.weekendIncome;
    }

    /**
     * 计算近日累计收益
     *
     * @param date   时间字符串 yyyy-MM-dd
     * @param income 时间对应的收益
     * @return 收益结果
     */
    private BigDecimal calTodayIncome(String date, Integer income) {
        if (Objects.equals(this.today, date)) {
            return NumChainCal.startOf(this.todayIncome).add(income).getBigDecimal();
        }
        return this.todayIncome;
    }
}
