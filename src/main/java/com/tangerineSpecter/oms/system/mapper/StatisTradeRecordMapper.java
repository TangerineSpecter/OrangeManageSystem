package com.tangerinespecter.oms.system.mapper;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.mapper.BaseMapperX;
import com.tangerinespecter.oms.common.mapper.QueryWrapperX;
import com.tangerinespecter.oms.common.query.TradeStatisQueryObject;
import com.tangerinespecter.oms.system.domain.entity.StatisTradeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交易记录统计表 Mapper 接口
 * </p>
 *
 * @author 丢失的橘子
 * @since 2024-01-12
 */
public interface StatisTradeRecordMapper extends BaseMapperX<StatisTradeRecord> {

    /**
     * 检测当前用户是否拥有统计数据
     *
     * @param uid 用户
     * @return true：拥有
     */
    default boolean checkHaveDataByUid(String uid) {
        return selectOne(new QueryWrapper<StatisTradeRecord>().eq("uid", uid).last("limit 1")) != null;
    }

    /**
     * 交易统计数据
     *
     * @param date 开始时间（包含），不存在则返回全部
     * @param uid  用户uid
     * @return 交易统计数据列表
     */
    default List<StatisTradeRecord> selectListByStartDate(String date, String uid) {
        return selectList(new QueryWrapperX<StatisTradeRecord>().geIfPresent("date", date).eq("uid", uid));
    }

    /**
     * 获取时间之前最后一条数据
     *
     * @param date 时间节点
     * @param uid  用户uid
     * @return 时间之前数据，无时间、无数据则返回空对象
     */
    default StatisTradeRecord selectPrevOneByDate(String date, String uid) {
        if(date == null) {
            return new StatisTradeRecord();
        }
        final StatisTradeRecord statisTradeRecord = selectOne(new QueryWrapperX<StatisTradeRecord>().lt("date", date)
            .eq("uid", uid).orderByDesc("date").last("limit 1"));
        return statisTradeRecord == null ? new StatisTradeRecord() : statisTradeRecord;
    }

    /**
     * 更新或新增新数据，数据为空，或者无id则新增
     *
     * @param data 数据
     */
    default void insertOrUpdate(StatisTradeRecord data) {
        if (data == null || data.getId() == null) {
            this.insert(data);
            return;
        }
        this.updateById(data);
    }

    /**
     * 根据年收益求和
     *
     * @param year 年份
     * @param date 求和截止时间（不包含此时间）
     * @param uid  用户uid
     * @return 总收益
     */
    int sumIncomeValueByYear(@Param("year") int year, @Param("date") String date, @Param("uid") String uid);

    /**
     * 根据月收益求和
     *
     * @param year  年份
     * @param month 月份
     * @param date  求和截止时间（不包含此时间）
     * @param uid   用户uid
     * @return 总收益
     */
    int sumIncomeValueByMonth(@Param("year") int year, @Param("month") int month, @Param("date") String date,
                              @Param("uid") String uid);

    /**
     * 根据年收益求和
     *
     * @param week 年份
     * @param date 求和截止时间（不包含此时间）
     * @param uid  用户uid
     * @return 总收益
     */
    int sumIncomeValueByWeek(@Param("year") int year, @Param("month") int month, @Param("week") int week,
                             @Param("date") String date, @Param("uid") String uid);

    /**
     * 获取去年最后结余资金，无则使用今年最初资金
     *
     * @param year 当前年份
     * @param uid  用户uid
     * @return 资金数据，无数据返回0
     */
    default int selectMoneyByYear(int year, String uid) {
        //获取当前时间之前的数据
        final StatisTradeRecord lastYearData = selectOne(new QueryWrapperX<StatisTradeRecord>().select("money")
            .eq("year", year - 1).eq("uid", uid).orderByDesc("date").last("limit 1"));
        if (lastYearData != null) {
            return lastYearData.getMoney();
        }
        //无数据则使用起始资金
        final StatisTradeRecord thisYearData = selectOne(new QueryWrapperX<StatisTradeRecord>()
            .select("money", "income_value").eq("year", year).eq("uid", uid).orderByAsc("date").last("limit 1"));
        if (thisYearData != null) {
            return thisYearData.getMoney() - thisYearData.getIncomeValue();
        }
        return 0;
    }

    /**
     * 获取上个月最后结余资金，无则使用本月最初资金
     *
     * @param date 查询时间
     * @param uid  用户uid
     * @return 资金数据，无数据返回0
     */
    default int selectMoneyByMonth(Date date, String uid) {
        //获取当前时间之前的数据
        final DateTime lastDate = DateUtil.offsetMonth(date, -1);
        final StatisTradeRecord lastMonthData = selectOne(new QueryWrapperX<StatisTradeRecord>().select("money")
            .eq("year", lastDate.year()).eq("month", lastDate.month() + 1).eq("uid", uid).orderByDesc("date")
            .last("limit 1"));
        if (lastMonthData != null) {
            return lastMonthData.getMoney();
        }
        //无数据则使用起始资金
        final DateTime thisDate = DateUtil.date(date);
        final StatisTradeRecord thisMonthData = selectOne(new QueryWrapperX<StatisTradeRecord>()
            .select("money", "income_value").eq("year", thisDate.year()).eq("month", thisDate.month() + 1)
            .eq("uid", uid).orderByAsc("date").last("limit 1"));
        if (thisMonthData != null) {
            return thisMonthData.getMoney() - thisMonthData.getIncomeValue();
        }
        return 0;
    }

    /**
     * 获取上周最后结余资金，无则使用本周最初资金
     *
     * @param date 查询时间
     * @param uid  用户uid
     * @return 资金数据，无数据返回0
     */
    default int selectMoneyByWeek(Date date, String uid) {
        //获取当前时间之前的数据
        final DateTime lastDate = DateUtil.offsetWeek(date, -1);
        final StatisTradeRecord lastMonthData = selectOne(new QueryWrapperX<StatisTradeRecord>().select("money")
            .eq("year", lastDate.year()).eq("month", lastDate.month() + 1).eq("week", lastDate.weekOfYear())
            .eq("uid", uid).orderByDesc("date").last("limit 1"));
        if (lastMonthData != null) {
            return lastMonthData.getMoney();
        }
        //无数据则使用起始资金
        final DateTime thisDate = DateUtil.date(date);
        final StatisTradeRecord thisMonthData = selectOne(new QueryWrapperX<StatisTradeRecord>()
            .select("money", "income_value").eq("year", thisDate.year()).eq("month", thisDate.month() + 1)
            .eq("week", thisDate.weekOfYear()).eq("uid", uid).orderByAsc("date").last("limit 1"));
        if (thisMonthData != null) {
            return thisMonthData.getMoney() - thisMonthData.getIncomeValue();
        }
        return 0;
    }

    /**
     * 获取指定时间统计数据
     *
     * @param date 时间 yyyy-MM-dd，不传默认最近一条
     * @return 统计数据
     */
    default StatisTradeRecord selectOneByDate(String date) {
        return selectOne(new QueryWrapperX<StatisTradeRecord>().eqIfPresent("date", date)
            .eq("uid", UserContext.getUid()).orderByDesc("date").last("limit 1"));
    }

    /**
     * 分页查询
     *
     * @param qo 高级查询条件
     * @return 分页结果
     */
    List<StatisTradeRecord> queryForPage(TradeStatisQueryObject qo);

    /**
     * 根据时间删除统计数据
     *
     * @param date 时间
     */
    default void deleteByDate(String date) {
        delete(new QueryWrapper<StatisTradeRecord>()
            .eq("date", date)
            .eq("uid", UserContext.getUid()));
    }

    /**
     * 根据类型返回统计数据
     * @param uid 账号uid
     * @param type 1：每天；2：每月
     * @return 统计数据
     */
    List<StatisTradeRecord> selectListByType(@Param("uid") String uid, @Param("type") Integer type);
}
