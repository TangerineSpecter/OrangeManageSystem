package com.tangerinespecter.oms.system.mapper;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据代码获取最后一条数据
     *
     * @param code 货币代码
     * @return 汇率数据
     */
    default DataExchangeRate selectLastOneByCode(String code) {
        if (CharSequenceUtil.isEmpty(code)) {
            return new DataExchangeRate();
        }
        return selectOne(new QueryWrapper<DataExchangeRate>()
            .eq("code", code)
            .orderByDesc("record_time")
            .last("limit 1"));
    }

    /**
     * 获取最近记录的汇率数据
     *
     * @param date 指定最近时间，不指定则取最新数据
     * @param code 代码，无则查全部代码
     * @return 汇率数据
     */
    List<DataExchangeRate> selectListByLastRecordTime(@Param("date") String date, @Param("code") String code);

    /**
     * 获取最近记录的汇率数据
     *
     * @param date 指定最近时间，不指定则取最新数据
     * @param code 代码，无则查全部代码
     * @return 汇率数据
     */
    DataExchangeRate selectOneByLastRecordTime(@Param("date") String date, @Param("code") String code);

    /**
     * 插入数据存在则忽略
     *
     * @param insertData 插入数据
     * @return 成功条数
     */
    int insertIgnore(@Param("data") DataExchangeRate insertData);

    /**
     * 根据时间和code查询汇率信息
     *
     * @param code       代码
     * @param recordTime 记录时间
     * @return 汇率信息
     */
    default DataExchangeRate selectOneByRecordTime(String code, String recordTime) {
        return selectOne(new QueryWrapper<DataExchangeRate>()
            .eq("code", code)
            .eq("recode_time", recordTime)
            .last("limit 1"));
    }
}
