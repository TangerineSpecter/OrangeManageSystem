package com.tangerinespecter.oms.system.event.data;

import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.redis.RedisKey;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.data.IDataTradeRecordService;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeDataEventListener {

    private final ITradeStatisService tradeStatisService;
    private final IDataTradeRecordService tradeRecordService;

    private final SqlSessionFactory sqlSessionFactory;
    private final RedisHelper redisHelper;


    /**
     * 异步批量计算交易记录以及更新统计数据
     *
     * @param date 时间，yyyy-MM-dd
     */
    @Async
    @EventListener
    public void asyncBatchCalTradeDateByDate(String date) {
        log.info("重新计算时间[{}]之后交易数据", date);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        //交易记录数据入库
        DataTradeRecordMapper sessionTradeMapper = sqlSession.getMapper(DataTradeRecordMapper.class);
        try {
            final List<DataTradeRecord> dataTradeRecords = sessionTradeMapper.selectListByStartDate(date, UserContext.getUid());
            CollUtils.forEach(dataTradeRecords, data -> sessionTradeMapper.updateById(this.tradeRecordService.fillSingleTradeData(data)));
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        //统计数据处理
        tradeStatisService.statisTradeDataByDate(date, UserContext.getUid());
    }
}
