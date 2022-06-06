package com.tangerinespecter.oms.job.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.JuheApiConfig;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.job.model.ExchangeListResponse;
import com.tangerinespecter.oms.job.model.ExchangeRateResponse;
import com.tangerinespecter.oms.system.domain.entity.DataExchange;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.mapper.DataExchangeMapper;
import com.tangerinespecter.oms.system.mapper.DataExchangeRateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateQuartzService {

    private final DataExchangeMapper dataExchangeMapper;
    private final DataExchangeRateMapper dataExchangeRateMapper;

    public void runData() {
        log.info("[执行汇率数据写入任务]");
        List<DataExchange> exchangeList = this.initCurrencyData();
        this.updateExchangeData(exchangeList);
        log.info("[汇率数据定时任务执行完毕]");
    }

    /**
     * 初始化货币列表
     */
    private List<DataExchange> initCurrencyData() {
        List<DataExchange> exchangeList = dataExchangeMapper.selectList(null);
        if (CollUtil.isEmpty(exchangeList)) {
            log.info("货币数据初始化...");
            try {
                HashMap<String, Object> params = MapUtil.newHashMap();
                params.put("key", JuheApiConfig.JUHE_EXCHANGE_API_KEY);
                String result = HttpUtil.post(JuheApiConfig.JUHE_CURRENCY_API_URL, params);
                ExchangeListResponse response = JSON.parseObject(result, ExchangeListResponse.class);
                Assert.isTrue(response.isSuccess(), "[货币列表接口请求失败],error_code:{}; reason:{}", response.getErrorCode(), response.getReason());
                List<DataExchange> dataExchangeList = Optional.of(response).map(ExchangeListResponse::getResult)
                        .map(ExchangeListResponse::getList).orElseThrow(() -> new BusinessException(RetCode.DATA_EXCEPTION));
                CollUtils.forEach(dataExchangeList, dataExchangeMapper::insert);
                exchangeList = dataExchangeList;
            } catch (Exception e) {
                log.error("[货币列表接口请求数据异常],异常信息： " + e);
            }
        }
        return exchangeList;
    }

    /**
     * 更新汇率信息
     *
     * @param exchangeList 货币列表
     */
    private void updateExchangeData(List<DataExchange> exchangeList) {
        log.info("[汇率信息写入任务]");
        //获取当日汇率
        List<DataExchangeRate> dataExchangeRates = dataExchangeRateMapper.selectListByRecordTime(DateUtil.today());
        try {
            if (CollUtil.size(dataExchangeRates) > 0) {
                CollUtils.forEach(dataExchangeRates, exchangeRate -> CommonConstant.EXCHANGE_RATE_MAP.put(exchangeRate.getCode(), NumChainCal.startOf(exchangeRate.getPrice()).div(100).getBigDecimal()));
                return;
            }
            HashMap<String, Object> params = MapUtil.newHashMap();
            params.put("key", JuheApiConfig.JUHE_EXCHANGE_API_KEY);
            String result = HttpUtil.post(JuheApiConfig.JUHE_EXCHANGE_API_URL, params);
            ExchangeRateResponse response = JSON.parseObject(result, ExchangeRateResponse.class);
            Assert.isTrue(response.isSuccess(), "[汇率接口请求失败],error_code:{}; reason:{}", response.getErrorCode(), response.getReason());
            List<List<String>> exchangeRateList = Optional.of(response).map(ExchangeRateResponse::getResult)
                    .map(ExchangeRateResponse::getList).orElseThrow(() -> new BusinessException(RetCode.DATA_EXCEPTION));
            CollUtils.forEach(this.convert2DbData(exchangeRateList, exchangeList), dataExchangeRateMapper::insert);
        } catch (Exception e) {
            log.error("[货币汇率接口请求数据异常],异常信息： " + e);
        }
        log.info("汇率更新完毕");
    }

    /**
     * 转化为入库模型
     *
     * @param exchangeRateList 汇率列表
     * @param exchangeList     货币列表
     * @return db模型
     */
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
