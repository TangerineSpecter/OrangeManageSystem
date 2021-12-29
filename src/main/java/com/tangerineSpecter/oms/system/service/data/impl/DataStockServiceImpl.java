package com.tangerinespecter.oms.system.service.data.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataStock;
import com.tangerinespecter.oms.system.mapper.DataStockMapper;
import com.tangerinespecter.oms.system.service.data.IDataStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataStockServiceImpl implements IDataStockService {
	
	@Resource
	private DataStockMapper dataStockMapper;
	
	@Override
	public ServiceResult queryForPage(StockQueryObject qo) {
		PageInfo<DataStock> stockPageInfo = PageHelper.startPage(qo.getPage(), qo.getLimit())
				.doSelectPageInfo(() -> dataStockMapper.queryForPage(qo));
		return ServiceResult.pageSuccess(stockPageInfo);
	}
}
