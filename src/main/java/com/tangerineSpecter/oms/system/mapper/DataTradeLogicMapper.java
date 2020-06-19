package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataTradeLogicMapper extends BaseMapper<DataTradeLogic> {

    List<DataTradeLogic> queryForPage(TradeLogicQueryObject qo);
}
