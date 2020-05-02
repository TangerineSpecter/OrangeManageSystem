package com.tangerinespecter.oms.system.domain.dto.system;

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
     * 最近30日资金曲线
     */
    private String lastThirtyTotalMoney;
    /**
     * 最近30日期
     */
    private String lastThirtyDate;
}
