package com.tangerinespecter.oms.system.domain.dto.statis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 丢失的橘子
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("基金交易记录")
public class FundAnalysisTradeInfo implements Serializable {

    @ApiModelProperty("每份金钱")
    private BigDecimal perMoney = BigDecimal.ZERO;
    @ApiModelProperty("当前持仓金额")
    private BigDecimal amount = BigDecimal.ZERO;
    @ApiModelProperty("持有份额")
    private BigDecimal number = BigDecimal.ZERO;
    @ApiModelProperty("累计收益")
    private BigDecimal totalIncome = BigDecimal.ZERO;
    @ApiModelProperty("买入累计次数")
    private Integer buyCount = 1;

    public FundAnalysisTradeInfo(BigDecimal preMoney) {
        this.perMoney = preMoney;
    }

    /**
     * 卖出基金
     *
     * @param totalIncome 累计收益
     * @param perMoney    每份钱数
     */
    public void sellFund(BigDecimal totalIncome, BigDecimal perMoney) {
        this.totalIncome = totalIncome;
        //购买次数回到1
        this.buyCount = 1;
        //清空账户
        this.amount = BigDecimal.ZERO;
        this.perMoney = perMoney;
    }

    /**
     * 买入基金
     *
     * @param maxCount 允许最大买入次数
     * @return 买入结果，true：买入成功
     */
    public boolean buyFund(Integer maxCount) {
        //停止加仓
        if (this.buyCount >= maxCount) {
            return false;
        }
        //加仓份额，每次加仓金钱 / 当前净值，加上之前份额
        this.buyCount++;
        this.amount = this.amount.add(this.perMoney);
        return true;
    }

    /**
     * 初始化账户
     */
    public void initAmount() {
        this.amount = this.perMoney;
    }
}
