package com.tangerinespecter.oms.system.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
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

    /**
     * 获取最近一天的总收益
     *
     * @return
     */
    int getTotalIncomeByLastDay();

    /**
     * 获取最近一月的收益
     *
     * @return
     */
    int getTotalIncomeByLastMonth();

    /**
     * 获取最后一次交易日期
     *
     * @return
     */
    String getTradeLastDay();

    /**
     * 根据时间范围获取总收益值
     *
     * @param beginDate
     * @param endDate
     * @return 总收益
     */
    int getTotalIncomeByDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 获取最近30日资金信息
     *
     * @return
     */
    List<DataTradeRecord> getLastThirtyMoneyInfo();

    /**
     * 获取最近30天的资金收益信息
     */
    List<DataTradeRecord> queryTotalIncomeByDay();
}