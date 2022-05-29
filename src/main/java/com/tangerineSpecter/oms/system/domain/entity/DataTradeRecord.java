package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("交易时间")
    private String date;
    @ApiModelProperty("初始资金")
    private Integer startMoney;
    @ApiModelProperty("结束资金")
    private Integer endMoney;
    @ApiModelProperty("收益值")
    private BigDecimal incomeValue;
    @ApiModelProperty("收益率")
    private BigDecimal incomeRate;
    @ApiModelProperty("夏普比率")
    private BigDecimal sharpeRatio;
    @ApiModelProperty("胜率，百分比")
    private BigDecimal winRate;
    @ApiModelProperty("交易类型（0：股票；1：期货；2：外汇；3：基金）")
    private Integer type;
    @ApiModelProperty("币种，数据来源data_exchange_rate")
    private String currency;
    /**
     * 管理员Id
     */
    private String uid;
    /**
     * 创建时间
     */
    private Long createTime;
}
