package com.tangerinespecter.oms.system.service.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataStock;

public interface IDataStockService {

    /**
     * 股票池列表
     *
     * @param qo 查询参数
     * @return 分页结果
     */
    PageInfo<DataStock> queryForPage(StockQueryObject qo);
}
