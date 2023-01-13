package com.tangerinespecter.oms.system.service.table;

import com.tangerinespecter.oms.common.query.FundHistoryQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.service.BaseService;

import java.util.List;

public interface IDataFundHistoryService extends BaseService<FundHistoryQueryObject, DataFundHistory> {

    /**
     * 基金历史数据初始化
     *
     * @param fundCodes 基金代码集合
     */
    void initFundHistory(List<String> fundCodes);

    /**
     * 初始化全部基金历史数据
     */
    void initAllFundHistory();
}
