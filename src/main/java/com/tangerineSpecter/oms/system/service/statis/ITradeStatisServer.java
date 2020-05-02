package com.tangerinespecter.oms.system.service.statis;

import com.tangerinespecter.oms.common.result.ServiceResult;
import org.springframework.stereotype.Service;

public interface ITradeStatisServer {
    /**
     * 收益数据分析
     *
     * @return
     */
    ServiceResult incomeValueStatisInfo();
}
