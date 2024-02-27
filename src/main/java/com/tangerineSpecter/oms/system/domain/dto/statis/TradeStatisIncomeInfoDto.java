package com.tangerinespecter.oms.system.domain.dto.statis;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.entity.StatisTradeRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 收益统计数据
 *
 * @author 丢失的橘子
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeStatisIncomeInfoDto implements Serializable {

    @ApiModelProperty("所有类型累计总收益")
    private List<BigDecimal> allTotalIncome = CollUtil.newArrayList();
    @ApiModelProperty("时间")
    private List<String> date = CollUtil.newArrayList();

    @ApiModelProperty("交易记录时间")
    private List<String> tradeDate = CollUtil.newArrayList();
    @ApiModelProperty("交易记录单次收益数据")
    private List<BigDecimal> incomeValue = CollUtil.newArrayList();
    @ApiModelProperty("交易记录累计收益")
    private List<BigDecimal> totalIncome = CollUtil.newArrayList();

    /**
     * 增加展示交易统计数据
     *
     * @param data 数据
     */
    public void addTradeStatis(StatisTradeRecord data) {
        this.date.add(data.getDate());
        this.allTotalIncome.add(NumChainCal.fen2Yuan(data.getIncomeValue(), CommonConstant.DEFAULT_CURRENCY));
    }

    /**
     * 增加展示交易数据
     *
     * @param data 数据
     */
    public void addTradeData(DataTradeRecord data) {
        this.tradeDate.add(data.getDate());
        this.incomeValue.add(NumChainCal.fen2Yuan(data.getIncomeValue(), data.getCurrency()));
        this.totalIncome.add(NumChainCal.fen2Yuan(data.getTotalIncomeValue(), data.getCurrency()));
    }

}
