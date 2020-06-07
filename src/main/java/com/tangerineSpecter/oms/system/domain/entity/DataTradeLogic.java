package com.tangerinespecter.oms.system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 交易逻辑明细表
 *
 * @author TangerineSpecter
 * @version 0.4.1
 * @date 2020年06月05日11:35:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTradeLogic {

    private Long id;
    /**
     * 开仓时间
     */
    private String entryDate;
    /**
     * 平仓时间
     */
    private String exitDate;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型（0：股票；1：期货；2：外汇）
     */
    private Integer type;
    /**
     * 盈亏状态（-1：未平仓；0：盈利；1：亏损）
     */
    private Integer status;
    /**
     * 入场点
     */
    private BigDecimal entryPoint;
    /**
     * 出场点
     */
    private BigDecimal exitPoint;
    /**
     * 盈利点
     */
    private BigDecimal profitPoint;
    /**
     * 止损点
     */
    private BigDecimal lossPoint;
    /**
     * 当日收盘价
     */
    private BigDecimal closingPrice;
    /**
     * 交易逻辑
     */
    private String tradeLogic;
    /**
     * 当日复盘结论
     */
    private String conclusion;
    /**
     * 管理员ID
     */
    private Long adminId;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
}
