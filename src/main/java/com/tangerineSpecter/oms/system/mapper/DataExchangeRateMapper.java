package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;

import java.util.List;

public interface DataExchangeRateMapper extends BaseMapper<DataExchangeRate> {

    /**
     * 根据记录时间查询汇率
     *
     * @param recordTime 记录时间：yyyy-MM-dd
     * @return 汇率列表
     */
    default List<DataExchangeRate> selectListByRecordTime(String recordTime) {
        QueryWrapper<DataExchangeRate> queryWrapper = new QueryWrapper<DataExchangeRate>().eq("record_time", recordTime);
        return selectList(queryWrapper);
    }
}
