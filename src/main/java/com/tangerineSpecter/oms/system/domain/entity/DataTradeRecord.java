package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
@EqualsAndHashCode(callSuper = true)
public class DataTradeRecord extends AdminEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("交易时间")
    @TableField("date")
    private String date;
    @ApiModelProperty("初始资金")
    @TableField("start_money")
    private Integer startMoney = 0;
    @ApiModelProperty("结束资金")
    @TableField("end_money")
    private Integer endMoney = 0;
    @ApiModelProperty("收益值")
    @TableField("income_value")
    private Integer incomeValue = 0;
    @ApiModelProperty("累计收益值")
    @TableField("total_income_value")
    private Integer totalIncomeValue = 0;
    @ApiModelProperty("收益率")
    @TableField("income_rate")
    private BigDecimal incomeRate;
    @ApiModelProperty("夏普比率")
    @TableField("sharpe_ratio")
    private BigDecimal sharpeRatio;
    @ApiModelProperty("胜率，百分比")
    @TableField("win_rate")
    private BigDecimal winRate;
    @ApiModelProperty("交易类型（0：股票；1：期货；2：外汇；3：基金）")
    @TableField("type")
    private Integer type;
    @ApiModelProperty("币种，数据来源data_exchange_rate")
    @TableField("currency")
    private String currency;
    @ApiModelProperty("转入金额")
    @TableField("deposit")
    private Integer deposit = 0;
    @TableField("deposit_rate")
    @ApiModelProperty("转入金额汇率，每元兑换")
    private BigDecimal depositRate = BigDecimal.ZERO;
    @ApiModelProperty("转出金额")
    @TableField("withdrawal")
    private Integer withdrawal = 0;
    @TableField("withdrawal_rate")
    @ApiModelProperty("转出金额汇率，每元兑换")
    private BigDecimal withdrawalRate = BigDecimal.ZERO;
    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("当前汇率，用于计算出入金")
    @TableField(exist = false)
    private BigDecimal exchangeRate;

    /**
     * 总资金计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public Integer sumMoney(List<DataTradeRecord> tradeRecords) {
        return tradeRecords.stream().mapToInt(DataTradeRecord::sumEndMoney).sum();
    }

    /**
     * 总收益计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public int sumIncome(List<DataTradeRecord> tradeRecords) {
        return CollUtils.convertSumList(tradeRecords, tradeRecord -> this
            .sumMoney(tradeRecord.getIncomeValue(), tradeRecord.getCurrency()).getInteger());
    }

    /**
     * 单条数据计算
     *
     * @return 计算结果
     */
    public BigDecimal sumMoney() {
        return this.sumMoney(this.getIncomeValue(), this.getCurrency()).getBigDecimal(2);
    }

    /**
     * 单条数据计算
     *
     * @return 计算结果
     */
    public Integer sumMoney2Int() {
        return this.sumMoney(this.getIncomeValue(), this.getCurrency()).getInteger();
    }

    /**
     * 单条数据计算
     *
     * @return 计算结果
     */
    public Integer sumStartMoney() {
        return this.sumMoney(this.getStartMoney(), this.getCurrency()).getInteger();
    }

    /**
     * 单条数据计算
     *
     * @return 计算结果
     */
    public Integer sumEndMoney() {
        return this.sumMoney(this.getEndMoney(), this.getCurrency()).getInteger();
    }

    /**
     * 单条数据投入计算
     * 投入 = 入金 - 出金
     *
     * @return 计算结果
     */
    public Integer sumInputMoney() {
        return NumChainCal.startOf(this.sumMoney(this.getDeposit(), this.getCurrency()).getInteger())
            .sub(this.sumMoney(this.getWithdrawal(), this.getCurrency()).getInteger()).getInteger();
    }

    /**
     * 单条累计数据计算
     *
     * @return 计算结果
     */
    public BigDecimal sumTotalMoney() {
        return this.sumMoney(this.getTotalIncomeValue(), this.getCurrency()).getBigDecimal(2);
    }

    /**
     * 单条数据计算
     *
     * @param money    资金数据
     * @param currency 币种
     * @return 计算结果
     */
    public NumChainCal sumMoney(Integer money, String currency) {
        //如果设置了计算用利率则使用设置值，否则按照当前最新利率计算
        BigDecimal rate = this.exchangeRate != null && this.exchangeRate.compareTo(BigDecimal.ZERO) > 0 ? this.exchangeRate : CommonConstant.EXCHANGE_RATE_MAP.getOrDefault(currency, new BigDecimal(1));
        return NumChainCal.startOf(money).mul(rate);
    }

    /**
     * 单条数据计算
     *
     * @param money        资金数据
     * @param exchangeRate 自定义汇率
     * @return 计算结果
     */
    public NumChainCal sumMoney(Integer money, BigDecimal exchangeRate) {
        return NumChainCal.startOf(money).mul(exchangeRate);
    }
}
