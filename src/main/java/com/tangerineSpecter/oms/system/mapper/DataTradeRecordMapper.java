package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.mapper.BaseMapperX;
import com.tangerinespecter.oms.common.mapper.QueryWrapperX;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface DataTradeRecordMapper extends BaseMapperX<DataTradeRecord> {

    /**
     * 分页查询
     *
     * @param qo 查询条件
     * @return 列表数据
     */
    List<DataTradeRecord> queryForPage(TradeRecordQueryObject qo);

    /**
     * 根据id获取当前用户的交易记录
     *
     * @param id 数据id
     * @return 交易记录
     */
    default DataTradeRecord selectById(Long id) {
        return selectOne(new QueryWrapper<DataTradeRecord>()
            .eq("id", id)
            .eq("uid", UserContext.getUid()));
    }

    /**
     * 根据类型获取交易获胜次数
     *
     * @param type 类型
     * @param date 时间
     * @param uid  账户id
     * @return 次数
     */
    int getTradeWinCountByTypeAndDate(@Param("type") Integer type, @Param("date") String date,
                                      @Param("uid") String uid);

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
    int getTotalIncomeByDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate,
                             @Param("uid") String uid);

    /**
     * 获取最近30条资金信息
     *
     * @param uid 账户id
     * @return 记录列表
     */
    List<DataTradeRecord> getLastThirtyMoneyInfo(@Param("uid") String uid);

    /**
     * 获取最近30条的资金收益信息
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
     * @param type  TradeRecordTypeEnum枚举
     * @param limit 返回条数，不传则返回全部
     * @param isAsc true:时间正序排序，否则倒序
     * @return 记录列表
     */
    default List<DataTradeRecord> selectListByType(Integer type, Integer limit, boolean isAsc) {
        return selectList(new QueryWrapperX<DataTradeRecord>().eq("type", type).eq("uid", UserContext.getUid())
            .limitIfPresent(limit).orderBy(true, isAsc, "date"));
    }

    /**
     * 根据类型获取时间之前的记录数量
     *
     * @param type TradeRecordTypeEnum枚举
     * @param date 记录时间
     * @return 数量
     */
    default Long selectCountLeDateByType(Integer type, String date) {
        return selectCount(new QueryWrapper<DataTradeRecord>().eq("type", type).eq("uid", UserContext.getUid())
            .le("date", date).orderByAsc("date"));
    }

    /**
     * 根据类型获取最后一条数据结束资金
     *
     * @param type 类型
     * @param date 记录时间
     * @return 交易数据
     */
    default Integer selectLastEndMoneyByType(Integer type, String date) {
        final DataTradeRecord dataTradeRecord = selectOne(new QueryWrapper<DataTradeRecord>().select("end_money")
            .eq("type", type).eq("uid", UserContext.getUid()).le("date", date).orderByDesc("date").last("limit 1"));
        if (dataTradeRecord == null) {
            return 0;
        }
        return dataTradeRecord.getEndMoney();
    }

    /**
     * 获取今年以来的资金数据
     *
     * @param uid 管理员id
     * @return 资金数据
     */
    List<DataTradeRecord> selectListByThisYear(@Param("uid") String uid);

    /**
     * 根据limit获取最近的交易天数
     *
     * @param uid   管理员id
     * @param limit 数量
     * @return 日期数量
     */
    List<String> selectTradeDateList(@Param("uid") String uid, @Param("limit") int limit);

    /**
     * 根据时间范围获取交易数据
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return 交易数据
     */
    default List<DataTradeRecord> selectListByDate(String startDate, String endDate) {
        return selectList(new QueryWrapper<DataTradeRecord>().ge("date", startDate).le("date", endDate)
            .eq("uid", UserContext.getUid()).orderByDesc("date"));
    }

    /**
     * 根据类型分组后获取每组limit条数据
     *
     * @param uid   账号uid
     * @param limit 最近条数
     * @return 分组聚合数据结果
     */
    List<DataTradeRecord> selectRecentListByType(@Param("uid") String uid, @Param("limit") long limit);

    /**
     * 查询日期之前的最后一条数据
     *
     * @param type 类型 TradeRecordTypeEnum
     * @param date 时间日期
     * @return 交易数据
     */
    default DataTradeRecord selectLastOneBeforeDate(Integer type, String date) {
        return selectOne(new QueryWrapper<DataTradeRecord>().eq("uid", UserContext.getUid()).eq("type", type)
            .lt("date", date).orderByDesc("date").last("limit 1"));
    }

    /**
     * 根据起始时间查询交易记录
     *
     * @param date 大于等于开始时间（包含），不存在则返回全部
     * @param uid  用户uid
     * @return 交易记录数据
     */
    default List<DataTradeRecord> selectListByStartDate(String date, String uid) {
        return selectList(new QueryWrapperX<DataTradeRecord>().geIfPresent("date", date).eq("uid", uid)
            .orderByAsc("date"));
    }

    /**
     * 根据时间和类型查询用户记录数据
     *
     * @param date 时间
     * @param type 类型
     * @return
     */
    default DataTradeRecord selectOneByDateAndType(String date, Integer type) {
        return selectOne(new QueryWrapper<DataTradeRecord>().eq("date", date).eq("type", type)
            .eq("uid", UserContext.getUid()).last("limit 1"));
    }

    /**
     * 根据id以及用户id更新交易记录
     *
     * @param tradeRecord 交易记录
     * @return 更新结果
     */
    default int updateByUid(DataTradeRecord tradeRecord) {
        return update(tradeRecord, new UpdateWrapper<DataTradeRecord>()
            .eq("uid", UserContext.getUid())
            .eq("id", tradeRecord.getId()));
    }

    /**
     * 查询出每个类型距离当前时间最近的一条数据
     *
     * @param uid  用户uid
     * @param date 时间，yyyy-MM-dd
     * @return 数据集
     */
    List<DataTradeRecord> selectRecentListByDate(@Param("uid") String uid, @Param("date") String date);
}