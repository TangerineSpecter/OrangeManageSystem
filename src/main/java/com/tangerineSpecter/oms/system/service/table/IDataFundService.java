package com.tangerinespecter.oms.system.service.table;

import com.tangerinespecter.oms.common.query.FundQueryObject;
import com.tangerinespecter.oms.system.domain.dto.data.FundInitDataDto;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.service.BaseService;

public interface IDataFundService extends BaseService<FundQueryObject, DataFund> {

    /**
     * 基金数据初始化
     *
     * @return 初始化信息model
     */
    FundInitDataDto initFund();
}
