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
            //基金拆分日未持仓不支持买入跳过
            if (data.getSplit().compareTo(BigDecimal.ZERO) > 0 && tradeInfo.getNumber().compareTo(BigDecimal.ZERO) == 0) {
                return;
            }
            FundAnalysisInfoDto.AnalysisInfo info = new FundAnalysisInfoDto.AnalysisInfo(DateUtil.format(data.getDate(), DatePattern.NORM_DATE_PATTERN));
            result.getList().add(info);
            //基金拆分
            if (data.getSplit().compareTo(BigDecimal.ZERO) > 0) {
                tradeInfo.setNumber(NumChainCal.startOf(tradeInfo.getNumber()).mul(data.getSplit()).getBigDecimal());
                this.calFundIncome(info, tradeInfo, data.getNetValue(), vo.getMoney());
                return;
            }
            //份额为0则买入
            if (tradeInfo.getNumber().compareTo(BigDecimal.ZERO) == 0) {
                //买入份额 = 每份金钱 / 单位净值
                tradeInfo.setNumber(NumChainCal.startOf(tradeInfo.getPerMoney()).div(data.getNetValue()).getBigDecimal());
                info.setOperation(1);
                info.setNumber(tradeInfo.getNumber());
            }
            this.calFundIncome(info, tradeInfo, data.getNetValue(), vo.getMoney());
            //收益率超过卖出比例则卖出，卖出后隔日再买
            if (info.getRate().compareTo(vo.getGridSetting().getSellRate()) >= 0) {
                //重置每份钱数 = 起始资金 + 累计收益 / 份额
                final BigDecimal preMoney = NumChainCal.startOf(vo.getMoney()).add(info.getTotalIncome()).div(vo.getGridSetting().getNumber()).getBigDecimal();
                tradeInfo.sellFund(info.getTotalIncome(), preMoney);
                info.setOperation(2);
            } else if (info.getRate().compareTo(vo.getGridSetting().getBuyRate()) <= 0) {
                //低于买入比例则加仓
                tradeInfo.buyFund(vo.getGridSetting().getNumber(), data.getNetValue());
                info.setOperation(1);
                info.setNumber(tradeInfo.getNumber());
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
     * @param netValue  当天基金净值
     * @param money     起始本金
     */
    private void calFundIncome(FundAnalysisInfoDto.AnalysisInfo info, FundAnalysisTradeInfo tradeInfo, BigDecimal netValue, BigDecimal money) {
        //当前本金 = 每份金钱 * 买入次数
        BigDecimal thisMoney = NumChainCal.startOf(tradeInfo.getPerMoney()).mul(tradeInfo.getBuyCount()).getBigDecimal();
        //目前持有收益 = 份额 * 净值 - （当前本金）
        BigDecimal thisIncome = NumChainCal.startOf(tradeInfo.getNumber()).mul(netValue).sub(thisMoney).getBigDecimal(2);
        //目前累计收益 = 累计收益 + 本次收益
        final BigDecimal thisTotalIncome = NumChainCal.startOf(tradeInfo.getTotalIncome()).add(thisIncome).getBigDecimal();
        //目前累计收益率 = (累计收益 + 目前持有收益 / 起始资金) * 100
        BigDecimal thisTotalRate = NumChainCal.startOf(thisTotalIncome).div(money).mul(100).getBigDecimal(2);
        //本次持仓收益率
        BigDecimal rate = NumChainCal.startOf(thisIncome).div(thisMoney).mul(100).getBigDecimal(2);
        info.setIncome(thisIncome).setTotalIncome(thisTotalIncome).setRate(rate).setTotalRate(thisTotalRate);
    }

}
