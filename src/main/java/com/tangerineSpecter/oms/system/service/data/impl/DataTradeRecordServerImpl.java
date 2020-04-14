package com.tangerinespecter.oms.system.service.data.impl;

import cn.hutool.core.util.NumberUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.data.IDateTradeRecordServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class DataTradeRecordServerImpl implements IDateTradeRecordServer {

    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;


    @Override
    public ServiceResult queryForPage(TradeRecordQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<DataTradeRecord> pageList = dataTradeRecordMapper.queryForPage(qo);
        for (DataTradeRecord dto : pageList) {
            BigDecimal incomeRate = NumberUtil.div(dto.getIncomeValue(), dto.getStartMoney(), 2);
            dto.setIncomeRate(incomeRate);
        }
        PageInfo<DataTradeRecord> tradeRecordPageInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, tradeRecordPageInfo.getTotal());
    }

    @Override
    public ServiceResult init() {
        List<DataTradeRecord> datas = dataTradeRecordMapper.selectList(null);
        //获胜次数
        int winCount = 0;
        for (DataTradeRecord data : datas) {
            int incomeValue = data.getEndMoney() - data.getStartMoney();
            data.setIncomeValue(data.getEndMoney() - data.getStartMoney());
            data.setIncomeRate(NumberUtil.div(data.getIncomeValue(), data.getStartMoney(), 5));
            if (incomeValue >= 0) {
                winCount++;
            }
            data.setWinRate(new BigDecimal(NumberUtil.div(winCount, datas.size(), 5)));
            dataTradeRecordMapper.update(data, null);
        }
        return ServiceResult.success();
    }
}
