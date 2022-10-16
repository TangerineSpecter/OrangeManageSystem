package com.tangerinespecter.oms.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 聚合数据api配置
 */
@Configuration
public class JuheApiConfig {

    /**
     * 聚合数据接口Key
     */
    public static String JUHE_API_USER_KEY;
    /**
     * 聚合数据--汇率key
     */
    public static String JUHE_EXCHANGE_API_KEY;
    /**
     * 聚合数据--汇率Url
     */
    public static String JUHE_EXCHANGE_API_URL;
    /**
     * 聚合数据--货币列表Url
     */
    public static String JUHE_CURRENCY_API_URL;

    @Value("${juhe.api.key}")
    public void setJuheApiUserKey(String juheApiUserKey) {
        JuheApiConfig.JUHE_API_USER_KEY = juheApiUserKey;
    }

    @Value("${juhe.api.exchange.rate.key}")
    public void setJuheExchangeApiKey(String juheExchangeApiKey) {
        JuheApiConfig.JUHE_EXCHANGE_API_KEY = juheExchangeApiKey;
    }

    @Value("${juhe.api.exchange.rate.url}")
    public void setJuheExchangeApiUrl(String juheExchangeApiUrl) {
        JuheApiConfig.JUHE_EXCHANGE_API_URL = juheExchangeApiUrl;
    }

    @Value("${juhe.api.currency.list.url}")
    public void setJuheCurrencyApiUrl(String juheCurrencyApiUrl) {
        JuheApiConfig.JUHE_CURRENCY_API_URL = juheCurrencyApiUrl;
    }
}
