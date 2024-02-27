package com.tangerinespecter.oms.system.statis;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tangerinespecter.oms.common.config.JuheApiConfig;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.job.model.ExchangeRateResponse;
import com.tangerinespecter.oms.system.domain.dto.system.StatisticsInfo;
import com.tangerinespecter.oms.system.domain.entity.DataExchange;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.vo.system.ExecuteJobVo;
import com.tangerinespecter.oms.system.mapper.DataExchangeMapper;
import com.tangerinespecter.oms.system.mapper.DataExchangeRateMapper;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.data.IDataTradeRecordService;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import com.tangerinespecter.oms.system.service.system.IScheduledManageService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import com.tangerinespecter.oms.system.service.system.impl.SystemInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeStatisServiceImplTest {

    @Resource
    private ITradeStatisService tradeStatisService;
    @Resource
    private IDataTradeRecordService tradeRecordService;
    @Resource
    private SystemInfoServiceImpl systemInfoService;
    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;
    @Resource
    private DataExchangeMapper dataExchangeMapper;
    @Resource
    private DataExchangeRateMapper exchangeRateMapper;
    @Resource
    private ISystemUserService systemUserService;
    @Resource
    private IScheduledManageService scheduledManageService;

    @Test
    public void test() {
        DataTradeRecord data = new DataTradeRecord();
        final DataTradeRecord dataTradeRecord = dataTradeRecordMapper.selectLastOneBeforeDate(data.getType(), data.getDate());
        System.out.println(JSON.toJSONString(dataTradeRecord));
    }

    @Test
    public void test1() {
        final StatisticsInfo statisticsInfo = systemInfoService.getStatisticsInfo();
        System.out.println(JSON.toJSONString(statisticsInfo));
    }

    @Test
    public void test2() {
        List<DataExchange> exchangeList = dataExchangeMapper.selectList(null);
        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("key", JuheApiConfig.JUHE_EXCHANGE_API_KEY);
        String result = HttpUtil.post(JuheApiConfig.JUHE_EXCHANGE_API_URL, params);
        ExchangeRateResponse response = JSON.parseObject(result, ExchangeRateResponse.class);
        Assert.isTrue(response.isSuccess(), "[汇率接口请求失败],error_code:{}; reason:{}", response.getErrorCode(), response.getReason());
        List<List<String>> exchangeRateList = Optional.of(response).map(ExchangeRateResponse::getResult)
            .map(ExchangeRateResponse::getList).orElseThrow(() -> new BusinessException(RetCode.DATA_EXCEPTION));
        List<DataExchangeRate> responseRateDate = this.convert2DbData(exchangeRateList, exchangeList);
    }

    private List<DataExchangeRate> convert2DbData(List<List<String>> exchangeRateList,
                                                  List<DataExchange> exchangeList) {
        List<DataExchangeRate> result = CollUtil.newArrayList();
        Map<String, String> exchangeMap = CollUtils.convertMap(exchangeList, DataExchange::getName, DataExchange::getCode);
        for (List<String> list : exchangeRateList) {
            //index:5为中行折算价
            BigDecimal price = Convert.toBigDecimal(list.get(5));
            //index:0为货币名称
            String name = list.get(0);
            //价格未更新则跳过
            if (price == null) {
                log.info("[{}汇率未更新，跳过入库]", name);
                continue;
            }
            DataExchangeRate dbData = new DataExchangeRate();
            dbData.setName(name);
            dbData.setCode(exchangeMap.get(name));
            dbData.setPrice(price);
            dbData.setRecordTime(DateUtil.today());
            result.add(dbData);
        }
        return result;
    }

    @Test
    public void initRecord() throws InterruptedException {
        String uid = "8a279e62b91c0518";
        tradeRecordService.init();
        Thread.sleep(30000L);
    }

    @Test
    @BeforeAll
    public void login() {
        log.info("账号登录");
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "0c216110747bfbfaecb04350d3a0610f");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
    }

    @Test
    @DisplayName("数据统计")
    public void statis() {
        String uid = "8a279e62b91c0518";
//        this.login();
        tradeStatisService.statisTradeData(null, uid);
    }

    @Test
    @DisplayName("数据初始化")
    public void initTradeRecord() throws Exception {
//        this.login();
        tradeRecordService.init();
        Thread.sleep(60000L);
    }

    @Resource
    private SqlSessionFactory sqlSessionFactory;


    @Test
    public void time() {
        final List<DataExchangeRate> list = CollUtil.newArrayList();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {

            for (int i = 0; i < 1000; i++) {
                DataExchangeRateMapper sessionStatisMapper = sqlSession.getMapper(DataExchangeRateMapper.class);
                DataExchangeRateMapper sessionStatisMapper1 = sqlSession.getMapper(DataExchangeRateMapper.class);
                DataExchangeRateMapper sessionStatisMapper2 = sqlSession.getMapper(DataExchangeRateMapper.class);
                DataExchangeRateMapper sessionStatisMapper3 = sqlSession.getMapper(DataExchangeRateMapper.class);
                List<DataExchangeRate> dataExchangeRates = sessionStatisMapper.selectListByLastRecordTime("2024-01-01", null);
                List<DataExchangeRate> dataExchangeRates1 = sessionStatisMapper1.selectListByLastRecordTime("2024-01-01", null);
                List<DataExchangeRate> dataExchangeRates2 = sessionStatisMapper2.selectListByLastRecordTime("2024-01-01", null);
                List<DataExchangeRate> dataExchangeRates3 = sessionStatisMapper3.selectListByLastRecordTime("2024-01-01", null);
                list.addAll(dataExchangeRates);
                list.addAll(dataExchangeRates1);
                list.addAll(dataExchangeRates2);
                list.addAll(dataExchangeRates3);
            }
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void init() {
        String url = "https://www.chinamoney.com.cn/ags/ms/cm-u-bk-ccpr/CcprMthAvgHisNew?startYear=2020&startMonth=1&endYear=2024&endMonth=1&currency=USD/CNY&pageNum=1&pageSize=50";
        final String s = HttpUtil.get(url);
        System.out.println(s);

        final JSONArray records = JSON.parseObject(s).getJSONArray("records");
        for (int i = 0; i < records.size(); i++) {
            try {
                final JSONObject jsonObject = records.getJSONObject(i);
                final String date = jsonObject.getString("date") + "-01";
                final BigDecimal value = jsonObject.getJSONArray("values").getBigDecimal(0);
                final DataExchangeRate info = new DataExchangeRate();
                info.setName("美元");
                info.setCode("USD");
                info.setPrice(NumChainCal.startOf(value).mul(100).getBigDecimal(2));
                info.setRecordTime(date);
                System.out.println(JSON.toJSONString(info));
                exchangeRateMapper.insertIgnore(info);
            } catch (Exception e) {
                log.error("数据存在");
            }
        }

    }

    @Test
    public void testUpdate() {
        final DataTradeRecord dataTradeRecord = dataTradeRecordMapper.selectById(338L);
        dataTradeRecord.setRemark("测试修改");
        final int i = dataTradeRecordMapper.updateByUid(dataTradeRecord);
        System.out.println("修改数量：" + i);
    }

    @Test
    public void executeJob() {
        final ExecuteJobVo executeJobVo = new ExecuteJobVo();
        executeJobVo.setId(19L);
        executeJobVo.setParam("10");
        scheduledManageService.executeJob(executeJobVo);
    }

    @Test
    public void testInsert() {
        try {
            final DataTradeRecord dataTradeRecord = new DataTradeRecord();
            dataTradeRecord.setUid("8a279e62b91c0518");
            dataTradeRecord.setDate("2024-01-10");
            dataTradeRecord.setIncomeValue(100);
            dataTradeRecord.setType(2);
            dataTradeRecordMapper.insert(dataTradeRecord);
        } catch (DuplicateKeyException e) {
            log.error("索引冲突");
        } catch (Exception e) {
            log.error("异常：" + e);
        }
    }
}
