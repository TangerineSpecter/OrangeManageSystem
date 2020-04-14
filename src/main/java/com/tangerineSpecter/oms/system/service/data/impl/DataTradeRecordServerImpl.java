package com.tangerinespecter.oms.system.service.data.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.data.IDateTradeRecordServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataTradeRecordServerImpl implements IDateTradeRecordServer {

    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;


    @Override
    public ServiceResult queryForPage(TradeRecordQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<DataTradeRecord> pageList = dataTradeRecordMapper.queryForPage(qo);
        PageInfo<DataTradeRecord> tradeRecordPageInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, tradeRecordPageInfo.getTotal());
    }
}
