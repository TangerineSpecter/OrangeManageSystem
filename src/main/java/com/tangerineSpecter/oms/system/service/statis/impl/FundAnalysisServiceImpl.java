package com.tangerinespecter.oms.system.service.statis.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.convert.data.FundConvert;
import com.tangerinespecter.oms.system.domain.dto.statis.FundAnalysisInfoDto;
import com.tangerinespecter.oms.system.domain.dto.statis.FundAnalysisTradeInfo;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.domain.vo.statis.FundAnalysisInfoVo;
import com.tangerinespecter.oms.system.service.statis.IFundAnalysisService;
import com.tangerinespecter.oms.system.service.table.IDataFundHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 丢失的橘子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FundAnalysisServiceImpl implements IFundAnalysisService {

    private final IDataFundHistoryService fundHistoryService;

    @Override
    public FundAnalysisInfoDto analysis(FundAnalysisInfoVo vo) {
        FundAnalysisInfoDto result = new FundAnalysisInfoDto();
        //获取基金数据
        List<DataFundHistory> fundHistories = CollUtils.sortList(fundHistoryService.list(FundConvert.INSTANCE.convert(vo)), DataFundHistory::getDate);
        FundAnalysisTradeInfo tradeInfo = new FundAnalysisTradeInfo(NumChainCal.startOf(vo.getMoney()).div(vo.getGridSetting().getNumber()).getBigDecimal());
        AtomicInteger lossDay = new AtomicInteger();
        CollUtils.forEach(fundHistories, data -> {
            FundAnalysisInfoDto.AnalysisInfo info = new FundAnalysisInfoDto.AnalysisInfo(DateUtil.format(data.getDate(), DatePattern.NORM_DATE_PATTERN));
            result.getList().add(info);
            //账户为0则买入
            if (tradeInfo.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                tradeInfo.initAmount();
                info.setOperation(1);
                info.setMoney(tradeInfo.getPerMoney());
            }
            //账户金额 * 收益率 + 账户金额
            tradeInfo.setAmount(NumChainCal.startOf(tradeInfo.getAmount()).mul(data.getEarningsRate()).div(100).add(tradeInfo.getAmount()).getBigDecimal());
            this.calFundIncome(info, tradeInfo, vo.getMoney());
            //收益率超过卖出比例则卖出，卖出后隔日再买
            if (info.getRate().compareTo(vo.getGridSetting().getSellRate()) >= 0) {
                //重置每份钱数 = 起始资金 + 累计收益 / 份额
                final BigDecimal preMoney = NumChainCal.startOf(vo.getMoney()).add(info.getTotalIncome()).div(vo.getGridSetting().getNumber()).getBigDecimal();
                tradeInfo.sellFund(info.getTotalIncome(), preMoney);
                info.setOperation(2);
            } else if (info.getRate().compareTo(vo.getGridSetting().getBuyRate()) <= 0) {
                //低于买入比例则加仓
                boolean buyResult = tradeInfo.buyFund(vo.getGridSetting().getNumber());
                if (buyResult) {
                    info.setOperation(1);
                    info.setMoney(tradeInfo.getPerMoney());
                }
            }
            if (info.getRate().compareTo(BigDecimal.ZERO) < 0) {
                lossDay.getAndIncrement();
            } else {
                lossDay.set(0);
            }
            result.calPanelInfo(info.getTotalIncome(), info.getRate(), lossDay.get());
        });
        result.initStatisData();
        return result;
    }

    /**
     * 计算收益和收益率
     *
     * @param info      当天统计信息
     * @param tradeInfo 本次交易信息
     * @param money     起始本金
     */
    private void calFundIncome(FundAnalysisInfoDto.AnalysisInfo info, FundAnalysisTradeInfo tradeInfo, BigDecimal money) {
        //投入资金 = 每份金额 * 买入次数
        final BigDecimal buyMoney = NumChainCal.startOf(tradeInfo.getPerMoney()).mul(tradeInfo.getBuyCount()).getBigDecimal();
        //当前持有收益 = 账户金额 - 投入金额
        BigDecimal thisIncome = NumChainCal.startOf(tradeInfo.getAmount()).sub(buyMoney).getBigDecimal(2);
        //目前累计收益 = 累计收益 + 本次收益
        final BigDecimal thisTotalIncome = NumChainCal.startOf(tradeInfo.getTotalIncome()).add(thisIncome).getBigDecimal();
        //目前累计收益率 = (累计收益 + 目前持有收益 / 起始资金) * 100
        BigDecimal thisTotalRate = NumChainCal.startOf(thisTotalIncome).div(money).mul(100).getBigDecimal(2);
        //当前持仓收益率 = 当前持有收益 / 投入资金
        BigDecimal rate = NumChainCal.startOf(thisIncome).div(buyMoney).mul(100).getBigDecimal(2);
        info.setIncome(thisIncome).setTotalIncome(thisTotalIncome).setRate(rate).setTotalRate(thisTotalRate);
    }

}
