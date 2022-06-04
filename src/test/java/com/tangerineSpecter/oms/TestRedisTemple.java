package com.tangerinespecter.oms;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.JuheApiConfig;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.mapper.DataExchangeRateMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisTemple {

    @Resource
    private RedisTemplate<String, BigDecimal> redisTemplate;
    @Resource
    private DataExchangeRateMapper dataExchangeRateMapper;
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
        redisTemplate.opsForValue().set("DATA:EXCHANGE:RATE:", new BigDecimal(1));
    }
}
