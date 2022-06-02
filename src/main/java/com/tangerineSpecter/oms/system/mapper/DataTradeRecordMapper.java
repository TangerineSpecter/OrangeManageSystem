package com.tangerinespecter.oms.system.mapper;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface DataTradeRecordMapper extends BaseMapper<DataTradeRecord> {


    List<DataTradeRecord> queryForPage(TradeRecordQueryObject qo);

    /**
     * 根据类型获取交易获胜次数
     *
     * @param type 类型
     * @param date 时间
     * @param uid  账户id
     * @return 次数
     */
    int getTradeWinCountByTypeAndDate(@Param("type") Integer type, @Param("date") String date, @Param("uid") String uid);

    /**
     * 获取最近一天的总收益
     *
     * @param uid 账户id
     * @return 收益
     */
    int getTotalIncomeByLastDay(@Param("uid") String uid);

    /**
     * 获取最近一月的收益
     *
     * @param uid 账户id
     * @return 收益
     */
    int getTotalIncomeByLastMonth(@Param("uid") String uid);

    /**
     * 获取最后一次交易日期
     *
     * @param uid 账户id
     * @return 日期
     */
    String getTradeLastDay(@Param("uid") String uid);

    /**
     * 根据时间范围获取总收益值
     *
     * @param beginDate 起始时间
     * @param endDate   结束时间
     * @param uid       账户id
     * @return 总收益
     */
    int getTotalIncomeByDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("uid") String uid);

    /**
     * 获取最近30日资金信息
     *
     * @param uid 账户id
     * @return 记录列表
     */
    List<DataTradeRecord> getLastThirtyMoneyInfo(@Param("uid") String uid);

    /**
     * 获取最近30天的资金收益信息
     *
     * @param type TradeRecordTypeEnum枚举
     * @param uid  账户id
     * @return 数据列表
     */
    List<DataTradeRecord> queryTotalIncomeByDay(@Param("type") Integer type, @Param("uid") String uid);

    /**
     * 获取最近一年的收益
     *
     * @param uid 账户id
     * @return 收益
     */
    int getTotalIncomeByLastYear(@Param("uid") String uid);

    /**
     * 根据类型获取记录列表
     *
     * @param type TradeRecordTypeEnum枚举
     * @param uid  管理员id
     * @return 记录列表
     */
    default List<DataTradeRecord> selectListByType(Integer type, String uid) {
        if (CharSequenceUtil.isEmpty(uid)) {
            return Collections.emptyList();
        }
        return selectList(new QueryWrapper<DataTradeRecord>().eq("type", type)
                .eq("uid", uid)
                .orderByAsc("date"));
    }

    /**
     * 根据类型获取时间之前的记录数量
     *
     * @param type TradeRecordTypeEnum枚举
     * @param date 记录时间
     * @param uid  管理员id
     * @return 数量
     */
    default Integer selectCountLeDateByType(Integer type, String date, String uid) {
        if (CharSequenceUtil.isEmpty(uid)) {
            return 0;
        }
        return selectCount(new QueryWrapper<DataTradeRecord>().eq("type", type)
                .eq("uid", uid)
                .le("date", date)
                .orderByAsc("date"));
    }

    /**
     * 根据类型获取最后一条数据结束资金
     *
     * @param type 类型
     * @param uid  管理员id
     * @return 交易数据
     */
    default Integer selectLastEndMoneyByType(Integer type, String uid) {
        if (CharSequenceUtil.isEmpty(uid)) {
            return 0;
        }
        return Optional.ofNullable(selectOne(new QueryWrapper<DataTradeRecord>().eq("type", type)
                .eq("uid", uid).orderByDesc("date").last("limit 1"))).orElse(new DataTradeRecord()).getEndMoney();
    }
}