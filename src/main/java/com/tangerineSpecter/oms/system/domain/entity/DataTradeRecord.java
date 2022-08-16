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
import java.util.Optional;

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
    private Integer deposit;
    @ApiModelProperty("转出金额")
    @TableField("withdrawal")
    private Integer withdrawal;
    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    /**
     * 初始化数据
     *
     * @param beforeData 上一条数据
     * @return 数据
     */
    public DataTradeRecord initData(DataTradeRecord beforeData) {
        if (beforeData == null) {
            //第一条数据进行重置计算
            this.totalIncomeValue = 0;
            this.withdrawal = 0;
            this.deposit = 0;
            return this;
        }
        //累计收益
        this.totalIncomeValue = beforeData.getTotalIncomeValue() + this.incomeValue;
        //前后金额差值
        int subtractMoney = this.startMoney - beforeData.getEndMoney();
        if (subtractMoney > 0) {
            this.deposit = subtractMoney;
        } else if (subtractMoney < 0) {
            this.withdrawal = Math.abs(subtractMoney);
        }
        return this;
    }

    /**
     * 总资金计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public Integer sumMoney(List<DataTradeRecord> tradeRecords) {
        return tradeRecords.stream().mapToInt(tradeRecord -> sumMoney(tradeRecord.getEndMoney(), tradeRecord.getCurrency()).getInteger()).sum();
    }

    /**
     * 总收益计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public int sumIncome(List<DataTradeRecord> tradeRecords) {
        return CollUtils.convertSumList(tradeRecords, tradeRecord -> this.sumMoney(tradeRecord.getIncomeValue(), tradeRecord.getCurrency()).getInteger());
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
        return NumChainCal.startOf(money)
                .mul(CommonConstant.EXCHANGE_RATE_MAP.getOrDefault(currency, new BigDecimal(1)))
                //单位分转元 除100
                .div(100);
    }
}
