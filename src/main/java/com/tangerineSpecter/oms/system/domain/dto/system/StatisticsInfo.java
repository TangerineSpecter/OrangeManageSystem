package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
     * 当月收益
     */
    private BigDecimal monthIncome;
    /**
     * 今天日期
     */
    private String today;
    /**
     * 本月
     */
    private String mouth;
    /**
     * 今日盈亏状态（0：盈利；1：亏损）
     */
    private Integer todayStatus;
    /**
     * 本月盈亏状态（0：盈利；1：亏损）
     */
    private Integer monthStatus;
}
