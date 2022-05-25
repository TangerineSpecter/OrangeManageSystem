package com.tangerinespecter.oms.system.service.statis;

import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;

public interface ITradeStatisService {
    /**
     * 收益数据分析
     *
     * @return 收益数据
     */
    TradeStatisIncomeInfoDto incomeValueStatisInfo();
}
