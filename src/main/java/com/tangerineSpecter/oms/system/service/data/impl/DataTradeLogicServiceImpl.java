package com.tangerinespecter.oms.system.service.data.impl;

import cn.hutool.core.lang.Assert;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.system.convert.data.TradeConvert;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import com.tangerinespecter.oms.system.domain.vo.data.AddTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.EditTradeLogicVo;
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
    public List<DataTradeLogic> list(TradeLogicQueryObject qo) {
        return dataTradeLogicMapper.queryForPage(qo);
    }

    @Override
    public void insertInfo(AddTradeLogicVo vo) {
        dataTradeLogicMapper.insert(TradeConvert.INSTANCE.convert(vo));
    }

    @Override
    public void updateInfo(EditTradeLogicVo vo) {
        int i = dataTradeLogicMapper.updateById(TradeConvert.INSTANCE.convert(vo));
        Assert.isTrue(i > 0, () -> new BusinessException(RetCode.TRADE_LOGIC_NOT_EXIST));
    }

    @Override
    public void deleteInfo(Long id) {
        dataTradeLogicMapper.deleteById(id);
    }
}
