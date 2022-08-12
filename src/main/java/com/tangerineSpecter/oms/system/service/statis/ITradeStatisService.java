package com.tangerinespecter.oms.system.service.statis;

import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;

public interface ITradeStatisService {
    /**
     * 收益数据分析
     * @param type 类型，1：每日；2：每月
     * @return 收益数据
     */
    TradeStatisIncomeInfoDto incomeValueStatisInfo(Integer type);
}
