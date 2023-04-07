package com.tangerinespecter.oms;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.JuheApiConfig;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.job.service.FundDataQuartzService;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.pojo.SystemVersionInfo;
import com.tangerinespecter.oms.system.domain.vo.statis.FundAnalysisInfoVo;
import com.tangerinespecter.oms.system.mapper.DataExchangeRateMapper;
import com.tangerinespecter.oms.system.mapper.DataFundHistoryMapper;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.data.impl.DataTradeRecordServiceImpl;
import com.tangerinespecter.oms.system.service.statis.IFundAnalysisService;
import com.tangerinespecter.oms.system.service.table.impl.DataFundHistoryServiceImpl;
import com.tangerinespecter.oms.system.service.tools.INlpToolService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisTemple {

    @Resource
    private RedisTemplate<String, BigDecimal> redisTemplate;
    @Resource
    private DataExchangeRateMapper dataExchangeRateMapper;
    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;
    @Resource
    private DataTradeRecordServiceImpl tradeRecordService;
    @Resource
    private FundDataQuartzService fundDataQuartzService;
    @Resource
    private DataFundHistoryMapper dataFundHistoryMapper;
    @Resource
    private SystemVersionInfo versionInfo;
    @Resource
    private IFundAnalysisService analysisService;
    @Resource
    private DataFundHistoryServiceImpl fundHistoryService;
    @Resource
    private INlpToolService nlpToolService;

    @Test
    public void version() {
        System.out.println(JSON.toJSONString(versionInfo));
    }
//
//    @Test
//    public void testRedis() {
//        //redisHelper.set("test1", "hello world");
//        //Object test1 = redisHelper.get("test1");
//        //System.out.println(test1.toString());
//    }

    @Test
    public void testConfig() {
//        System.out.println(JuheApiConfig.JUHE_CURRENCY_API_URL);
        final HashMap<String, Object> objectObjectHashMap = MapUtil.newHashMap();
        objectObjectHashMap.put("key", JuheApiConfig.JUHE_EXCHANGE_API_KEY);
        System.out.println(objectObjectHashMap);
//        final String post = HttpUtil.post(JuheApiConfig.JUHE_CURRENCY_API_URL, objectObjectHashMap);
//        System.out.println(post);
        final List<DataExchangeRate> dataExchangeRates = dataExchangeRateMapper.selectListByRecordTime(DateUtil.today());
        LocalDateTime recordTime = dataExchangeRates.get(0).getRecordTime();
        System.out.println(dataExchangeRates);
        System.out.println(DateUtil.compare(new Date(), DateUtil.date(recordTime), DatePattern.NORM_DATE_PATTERN));
    }

    @Test
    public void testSql() {
//        DataExchangeRate dataExchangeRate = dataExchangeRateMapper.selectById(null);
//        log.info(JSON.toJSONString(dataExchangeRate));
//        redisTemplate.opsForValue().set("DATA:EXCHANGE:RATE:", new BigDecimal(1));
        final Map<String, List<DataTradeRecord>> tradeRecordMap = CollUtils.convertMultiLinkedHashMap(dataTradeRecordMapper.selectListByThisYear("8a279e62b91c0518"), DataTradeRecord::getDate);
        System.out.println(JSON.toJSONString(tradeRecordMap));
        List<String> date = CollUtils.convertFilterList(tradeRecordMap.keySet(),
                key -> DateUtil.parse(key, DateFormat.getDateInstance()).getTime() > 1,
                r -> r);
        System.out.println(date);
        final DateTime parse = DateUtil.parse("2020-05-01", DateFormat.getDateInstance());
        System.out.println(parse.getTime());
        System.out.println(DateUtil.beginOfWeek(new Date(), true).getTime());
    }

    @Test
    public void numberCal() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
//            final Integer usd = NumChainCal.startOf(1)
//                    .mul(tradeRecordService.getExchangeRateByCode("USD"))
//                    .getInteger();
            final Integer usd = NumChainCal.startOf(1)
                    .mul(CommonConstant.EXCHANGE_RATE_MAP.get("USD"))
                    .getInteger();
            System.out.println(usd);
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
    }

    @Test
    public void fundData() {
        FundAnalysisInfoVo vo = new FundAnalysisInfoVo();
        vo.setCode("161725");
        vo.setStartTime("2010-10-01");
        vo.setEndTime("2022-01-01");
        vo.setMoney(new BigDecimal(100000));
        analysisService.analysis(vo);
    }

    @Test
    public void daydayFund() throws Exception {
        String fundCode1 = "000072";
        String fundCode2 = "162703";
        
        fundHistoryService.handleFundSplitRate(fundCode2);
    }

    @Test
    public void nlpDemo() {
        final FileReader fileReader = new FileReader("");
        nlpToolService.analysis(null, fileReader.readString());
    }
}
