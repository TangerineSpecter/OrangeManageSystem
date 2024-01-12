package com.tangerinespecter.oms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.config.JuheApiConfig;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.enums.ScheduledTypeEnum;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.job.service.FundDataQuartzService;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.entity.SystemScheduledTask;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisTemple {

    private static final String content = "黄金P点：2064.59，R1:2109.14，S1:1984.58\n" +
            "白银P点：24.93，R1:25.46，S1:23.95\n" +
            "原油P点：73.63，R1:74.64，S1:72.25\n" +
            "欧美P点：1.0844，R1:1.0885：S1:1.0794\n" +
            "镑美P点：1.2653，R1:1.2703，S1:1.2582\n" +
            "澳美P点：0.6638，R1:0.6671，S1:0.6586\n" +
            "美加P点：1.3525，R1:1.3572，S1:1.3489\n" +
            "美日P点：146.95，R1:147.69，S1:146.46\n" +
            "纽美P点：0.6178，R1:0.6206，S1:0.6135";
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
    public void testSystemInfo() {
        System.out.println(JSON.toJSONString(SystemConstant.SYSTEM_CONFIG));
        SystemConstant.SYSTEM_CONFIG.setId(1L);
        System.out.println(JSON.toJSONString(SystemConstant.SYSTEM_CONFIG));
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
        fundHistoryService.getFundHistory(fundCode2, 1);
//        fundHistoryService.handleFundSplitRate(fundCode2);
    }

    @Test
    public void nlpDemo() {
        final FileReader fileReader = new FileReader("");
        nlpToolService.analysis(null, fileReader.readString());
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(content);
//        final List<String> strings = StrUtil.split(content, "\n");
//        for (String string : strings) {
////            System.out.println(string);
//            final List<String> split = StrUtil.split(string, "，");
////            System.out.println(split);
//            for (String s : split) {
//                System.out.println(s);
//            }
//        }
//        final Document document = Jsoup.connect("https://rili-d.jin10.com/open.php?fontSize=14px&theme=primary").get();
//        System.out.println(document);

        final Set<Class<?>> classes = ClassScanner.scanPackage("com.tangerinespecter.oms.job.quartz");
        System.out.println(classes);
        for (Class<?> clazz : classes) {
            final Object jobName = ReflectUtil.getFieldValue(clazz, "JOB_NAME");
            final Object jobCron = ReflectUtil.getFieldValue(clazz, "JOB_CRON");
            SystemScheduledTask task = new SystemScheduledTask();
            task.setClassPath(clazz.getName());
            task.setCron(Convert.toStr(jobCron));
            task.setName(Convert.toStr(jobName));
            task.setType(ScheduledTypeEnum.DEFAULT.getValue());
            System.out.println(JSON.toJSONString(task));
        }
    }

}
