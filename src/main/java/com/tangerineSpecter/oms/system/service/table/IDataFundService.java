package com.tangerinespecter.oms.system.service.table;

import com.tangerinespecter.oms.common.query.FundQueryObject;
import com.tangerinespecter.oms.system.domain.dto.data.FundInitDataDto;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.service.BaseService;

import java.util.List;

public interface IDataFundService extends BaseService<FundQueryObject, DataFund> {

    /**
     * 基金数据初始化
     *
     * @return 初始化信息model
     */
    FundInitDataDto initFund();

    /**
     * 基金历史数据初始化
     *
     * @param fundCodes 基金代码集合
     */
    void initFundHistory(List<String> fundCodes);
}
