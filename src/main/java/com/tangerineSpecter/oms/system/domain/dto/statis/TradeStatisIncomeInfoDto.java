package com.tangerinespecter.oms.system.domain.dto.statis;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
@AllArgsConstructor
public class TradeStatisIncomeInfoDto implements Serializable {

    /**
     * 总收益
     */
    private List<BigDecimal> totalIncome;
    /**
     * 股票时间
     */
    private List<String> stockDate;
    /**
     * 股票数据
     */
    private List<BigDecimal> stockIncome;
    /**
     * 股票累计收益
     */
    private List<BigDecimal> stockTotalIncome;
    /**
     * 期货时间
     */
    private List<String> futuresDate;
    /**
     * 期货数据
     */
    private List<BigDecimal> futuresIncome;
    /**
     * 期货累计收益
     */
    private List<BigDecimal> futuresTotalIncome;
    /**
     * 外汇时间
     */
    private List<String> foreignDate;
    /**
     * 外汇数据
     */
    private List<BigDecimal> foreignIncome;
    /**
     * 外汇累计收益
     */
    private List<BigDecimal> foreignTotalIncome;
    /**
     * 基金时间
     */
    private List<String> fundsDate;
    /**
     * 基金数据
     */
    private List<BigDecimal> fundsIncome;
    /**
     * 基金累计收益
     */
    private List<BigDecimal> fundsTotalIncome;
    /**
     * 时间
     */
    private List<String> date;

    public TradeStatisIncomeInfoDto() {
        //总收益
        this.totalIncome = CollUtil.newArrayList();
        //时间
        this.stockDate = CollUtil.newArrayList();
        this.futuresDate = CollUtil.newArrayList();
        this.foreignDate = CollUtil.newArrayList();
        this.fundsDate = CollUtil.newArrayList();
        //收益数据
        this.stockIncome = CollUtil.newArrayList();
        this.futuresIncome = CollUtil.newArrayList();
        this.foreignIncome = CollUtil.newArrayList();
        this.fundsIncome = CollUtil.newArrayList();
        //总收益数据
        this.stockTotalIncome = CollUtil.newArrayList();
        this.futuresTotalIncome = CollUtil.newArrayList();
        this.foreignTotalIncome = CollUtil.newArrayList();
        this.fundsTotalIncome = CollUtil.newArrayList();
        //时间
        this.date = CollUtil.newArrayList();
    }

    /**
     * 初始化股票数据
     *
     * @param date        时间
     * @param tradeRecord 交易数据
     * @return 收益
     */
    public BigDecimal initStockIncome(String date, DataTradeRecord tradeRecord) {
        if (tradeRecord == null || !TradeRecordTypeEnum.STOCK_TYPE.getValue().equals(tradeRecord.getType())) {
            return BigDecimal.ZERO;
        }
        this.stockDate.add(date);
        this.stockIncome.add(tradeRecord.sumMoney());
        this.stockTotalIncome.add(tradeRecord.sumTotalMoney());
        return CollUtil.getLast(this.stockIncome);
    }

    /**
     * 初始化期货数据
     *
     * @param date        时间
     * @param tradeRecord 交易数据
     */
    public BigDecimal initFuturesIncome(String date, DataTradeRecord tradeRecord) {
        if (tradeRecord == null || !TradeRecordTypeEnum.FUTURES_TYPE.getValue().equals(tradeRecord.getType())) {
            return BigDecimal.ZERO;
        }
        this.futuresDate.add(date);
        this.futuresIncome.add(tradeRecord.sumMoney());
        this.futuresTotalIncome.add(tradeRecord.sumTotalMoney());
        return CollUtil.getLast(this.futuresIncome);
    }

    /**
     * 初始化外汇数据
     *
     * @param date        时间
     * @param tradeRecord 交易数据
     * @return 收益
     */
    public BigDecimal initForeignIncome(String date, DataTradeRecord tradeRecord) {
        if (tradeRecord == null || !TradeRecordTypeEnum.FOREIGN_EXCHANGE_TYPE.getValue().equals(tradeRecord.getType())) {
            return BigDecimal.ZERO;
        }
        this.foreignDate.add(date);
        this.foreignIncome.add(tradeRecord.sumMoney());
        this.foreignTotalIncome.add(tradeRecord.sumTotalMoney());
        return CollUtil.getLast(this.foreignIncome);
    }

    /**
     * 初始化基金数据
     *
     * @param date        时间
     * @param tradeRecord 交易数据
     * @return 收益
     */
    public BigDecimal initFundsIncome(String date, DataTradeRecord tradeRecord) {
        if (tradeRecord == null || !TradeRecordTypeEnum.FUND_TYPE.getValue().equals(tradeRecord.getType())) {
            return BigDecimal.ZERO;
        }
        this.fundsDate.add(date);
        this.fundsIncome.add(tradeRecord.sumMoney());
        this.fundsTotalIncome.add(tradeRecord.sumTotalMoney());
        return CollUtil.getLast(this.fundsIncome);
    }

    /**
     * 初始化节点时间收益数据
     *
     * @param date             时间
     * @param dataTradeRecords 交易数据
     */
    public void initAllIncome(String date, List<DataTradeRecord> dataTradeRecords) {
        NumChainCal numChainCal = NumChainCal.startOf(0);
        CollUtils.forEach(dataTradeRecords, tradeRecord ->
                numChainCal.add(
                        this.initStockIncome(date, tradeRecord),
                        this.initFuturesIncome(date, tradeRecord),
                        this.initForeignIncome(date, tradeRecord),
                        this.initFundsIncome(date, tradeRecord)
                ));
        //汇总返回
        this.totalIncome.add(numChainCal.getBigDecimal());
    }
}
