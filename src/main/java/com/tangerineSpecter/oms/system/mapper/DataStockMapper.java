package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataStock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataStockMapper extends BaseMapper<DataStock> {


    List<DataStock> queryForPage(StockQueryObject qo);

}