package com.tangerinespecter.oms.job.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
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

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateQuartzService {

    private final DataExchangeMapper dataExchangeMapper;
    private final DataExchangeRateMapper dataExchangeRateMapper;

    @PostConstruct
    public void init() {
        List<DataExchangeRate> dataExchangeRates = dataExchangeRateMapper.selectListByLastRecordTime(null, null);
        CollUtils.forEach(dataExchangeRates, exchangeRate -> CommonConstant.EXCHANGE_RATE_MAP.put(exchangeRate.getCode(), NumChainCal
            .startOf(exchangeRate.getPrice()).div(100).getBigDecimal()));
        //默认CNY利率
        CommonConstant.EXCHANGE_RATE_MAP.put(CommonConstant.DEFAULT_CURRENCY, BigDecimal.ONE);
        log.info("[初始化货币汇率完毕]，数量：{}", CommonConstant.EXCHANGE_RATE_MAP.keySet().size());
    }

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
                //数据处理
                List<DataExchange> dataExchangeList = Optional.of(response).map(ExchangeListResponse::getResult)
                    .map(ExchangeListResponse::getList)
                    .orElseThrow(() -> new BusinessException(RetCode.DATA_EXCEPTION));
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
     * 3小时执行一次
     *
     * @param exchangeList 货币列表
     */
    private void updateExchangeData(List<DataExchange> exchangeList) {
        log.info("[汇率信息写入任务]");
        //获取最近一次汇率，避免凌晨时分数据没更新而失效
//        List<DataExchangeRate> dataExchangeRates = dataExchangeRateMapper.selectListByLastRecordTime();
        try {
            //早上8点前不更新数据
//            DateTime morning = DateUtil.offsetHour(DateUtil.beginOfDay(new Date()), 8);
//            if (DateUtil.compare(new Date(), morning) == -1) {
//                log.info("[当前时间为8.AM之前，不进行汇率更新]");
////                CollUtils.forEach(dataExchangeRates, exchangeRate -> CommonConstant.EXCHANGE_RATE_MAP.put(exchangeRate.getCode(), NumChainCal.startOf(exchangeRate.getPrice()).div(100).getBigDecimal()));
//                return;
//            }
            //当天数据则不更新数据
//            if (Objects.equals(CollUtil.getFirst(dataExchangeRates).getRecordTime().toLocalDate().toString(), DateUtil.today())) {
//                log.info("[当天汇率数据已更新，不进行数据处理]");
//                CollUtils.forEach(dataExchangeRates, exchangeRate -> CommonConstant.EXCHANGE_RATE_MAP.put(exchangeRate.getCode(), NumChainCal.startOf(exchangeRate.getPrice()).div(100).getBigDecimal()));
//                return;
//            }
            HashMap<String, Object> params = MapUtil.newHashMap();
            params.put("key", JuheApiConfig.JUHE_EXCHANGE_API_KEY);
            String result = HttpUtil.post(JuheApiConfig.JUHE_EXCHANGE_API_URL, params);
            ExchangeRateResponse response = JSON.parseObject(result, ExchangeRateResponse.class);
            Assert.isTrue(response.isSuccess(), "[汇率接口请求失败],error_code:{}; reason:{}", response.getErrorCode(), response.getReason());
            List<List<String>> exchangeRateList = Optional.of(response).map(ExchangeRateResponse::getResult)
                .map(ExchangeRateResponse::getList)
                .orElseThrow(() -> new BusinessException(RetCode.DATA_EXCEPTION));
            List<DataExchangeRate> responseRateDate = this.convert2DbData(exchangeRateList, exchangeList);
            CollUtils.forEach(responseRateDate, dataExchangeRateMapper::insertIgnore);
            CollUtils.forEach(responseRateDate, exchangeRate -> CommonConstant.EXCHANGE_RATE_MAP.put(exchangeRate.getCode(), NumChainCal
                .startOf(exchangeRate.getPrice()).div(100).getBigDecimal()));
        } catch (Exception e) {
            log.error("[货币汇率接口请求数据异常],异常信息： " + e);
        }
        log.info("[汇率更新完毕]");
    }

    /**
     * 转化为入库模型
     *
     * @param exchangeRateList 汇率列表
     * @param exchangeList     货币列表
     * @return db模型
     */
    private List<DataExchangeRate> convert2DbData(List<List<String>> exchangeRateList,
                                                  List<DataExchange> exchangeList) {
        List<DataExchangeRate> result = CollUtil.newArrayList();
        Map<String, String> exchangeMap = CollUtils.convertMap(exchangeList, DataExchange::getName, DataExchange::getCode);
        for (List<String> list : exchangeRateList) {
            //index:0为货币名称
            String name = list.get(0);
            //index:5为中行折算价
            BigDecimal price = Convert.toBigDecimal(list.get(5));
            final String code = exchangeMap.get(name);
            //价格未更新 或者 货币列表不存在货币代码跳过 则跳过
            if (price == null || code == null) {
                log.info("[{}汇率数据异常，跳过入库]", name);
                continue;
            }
            result.add(new DataExchangeRate(name, code, price));
        }
        return result;
    }

}
