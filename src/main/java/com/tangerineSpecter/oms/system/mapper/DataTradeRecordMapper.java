package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataTradeRecordMapper extends BaseMapper<DataTradeRecord> {


    List<DataTradeRecord> queryForPage(TradeRecordQueryObject qo);

    /**
     * 根据类型获取交易获胜次数
     *
     * @param type
     * @return
     */
    int getTradeWinCountByTypeAndDate(@Param("type") Integer type, @Param("date") String date);
}