package com.tangerinespecter.oms.system.statis;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.JuheApiConfig;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.job.model.ExchangeRateResponse;
import com.tangerinespecter.oms.system.domain.dto.system.StatisticsInfo;
import com.tangerinespecter.oms.system.domain.entity.DataExchange;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.mapper.DataExchangeMapper;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import com.tangerinespecter.oms.system.service.system.impl.SystemInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeStatisServiceImplTest {

    @Resource
    private ITradeStatisService tradeStatisService;
    @Resource
    private SystemInfoServiceImpl systemInfoService;
    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;
    @Resource
    private DataExchangeMapper dataExchangeMapper;


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

    private List<DataExchangeRate> convert2DbData(List<List<String>> exchangeRateList, List<DataExchange> exchangeList) {
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
            dbData.setRecordTime(DateUtil.toLocalDateTime(new Date()));
            result.add(dbData);
        }
        return result;
    }
}
