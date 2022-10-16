package com.tangerinespecter.oms.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 基金数据api配置
 *
 * @author zhouliangjun
 */
@Configuration
public class FundApiConfig {

    /**
     * 所有基金列表接口
     *
     * @link https://www.doctorxiong.club/api/#api-Fund-getAllFund
     */
    public static final String ALL_FUND_LIST_API = "https://api.doctorxiong.club/v1/fund/all";
    /**
     * 蛋卷基金-历史净值api
     * 通过基金code查询，默认查10000条
     */
    public static final String DJ_FUND_HISTORY_API = "https://danjuanfunds.com/djapi/fund/nav/history/{}?page=1&size={}";


}
