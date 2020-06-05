package com.tangerinespecter.oms.system.mapper;

import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataTradeLogicMapper {

    List<DataTradeLogic> queryForPage(TradeLogicQueryObject qo);
}
