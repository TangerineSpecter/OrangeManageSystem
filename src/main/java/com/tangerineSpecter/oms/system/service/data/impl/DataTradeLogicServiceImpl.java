package com.tangerinespecter.oms.system.service.data.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import com.tangerinespecter.oms.system.mapper.DataTradeLogicMapper;
import com.tangerinespecter.oms.system.service.data.IDataTradeLogicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataTradeLogicServiceImpl implements IDataTradeLogicService {

    @Resource
    private DataTradeLogicMapper dataTradeLogicMapper;

    @Override
    public ServiceResult queryForPage(TradeLogicQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<DataTradeLogic> pageList = dataTradeLogicMapper.queryForPage(qo);
        PageInfo<DataTradeLogic> tradeLogicPageInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, tradeLogicPageInfo.getTotal());
    }
}
