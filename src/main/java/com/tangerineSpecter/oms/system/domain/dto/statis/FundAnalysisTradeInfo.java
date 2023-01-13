package com.tangerinespecter.oms.system.domain.dto.statis;

import com.tangerinespecter.oms.common.utils.NumChainCal;
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
        //清空份额
        this.number = BigDecimal.ZERO;
        this.perMoney = perMoney;
    }

    /**
     * 买入基金
     *
     * @param maxCount 允许最大买入次数
     * @param netValue 目前买入基金净值
     * @return 买入结果，true：买入成功
     */
    public boolean buyFund(Integer maxCount, BigDecimal netValue) {
        //停止加仓
        if (this.buyCount >= maxCount) {
            return false;
        }
        //加仓份额，每次加仓金钱 / 当前净值，加上之前份额
        BigDecimal addNumber = NumChainCal.startOf(this.perMoney).div(netValue).getBigDecimal();
        this.buyCount++;
        this.number = this.number.add(addNumber);
        return true;
    }
}
