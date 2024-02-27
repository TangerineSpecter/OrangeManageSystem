package com.tangerinespecter.oms.system.service.statis;

import com.tangerinespecter.oms.common.query.TradeStatisQueryObject;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.domain.entity.StatisTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.service.BaseService;

/**
 * @author 丢失的橘子
 */
public interface ITradeStatisService extends BaseService<TradeStatisQueryObject, StatisTradeRecord> {

    /**
     * 统计计算交易记录数据
     *
     * @param day 不传默认全部，否则 1表示近一天，7表示近七天
     * @param uid 处理账号，不传默认全部
     */
    void statisTradeData(Integer day, String uid);

    /**
     * 统计计算交易记录数据
     *
     * @param date，格式yyyy-MM-dd 不传默认全部，否则 统计计算日期之后的交易记录数据
     * @param uid               处理账号，不传默认全部
     */
    void statisTradeDataByDate(String date, String uid);

    /**
     * 收益数据分析
     *
     * @param type 类型，1：每日；2：每月
     * @param tradeType 交易类型
     * @return 收益数据
     */
    TradeStatisIncomeInfoDto incomeValueStatisInfo(Integer type, TradeRecordTypeEnum tradeType);

    /**
     * 获取统计数据
     *
     * @param date yyyy-MM-dd，不传则返回最近一条
     * @return 统计数据
     */
    StatisTradeRecord getStatisData(String date);

    /**
     * 根据时间清理并刷新时间之后统计数据
     *
     * @param date 时间，yyyy-MM-dd
     * @return true：处理完毕
     */
    boolean deleteRefreshByDate(String date);
}
