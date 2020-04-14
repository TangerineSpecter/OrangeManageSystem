package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 交易记录表
 *
 * @author tangerineSpecter
 * @date 2020年04月14日10:12:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTradeRecord {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 交易时间
     */
    private String date;
    /**
     * 初始资金）
     */
    private Integer startMoney;
    /**
     * 结束资金
     */
    private Integer endMoney;
    /**
     * 收益值
     */
    private BigDecimal incomeValue;
    /**
     * 收益率
     */
    private BigDecimal incomeRate;
    /**
     * 夏普比率
     */
    private BigDecimal sharpeRatio;
    /**
     * 胜率
     */
    private BigDecimal winRate;
    /**
     * 交易类型（0：股票；1：期货；2：晚会）
     */
    private Integer type;
    /**
     * 管理员Id
     */
    private Long adminId;
    /**
     * 创建时间
     */
    private Long createTime;
}
