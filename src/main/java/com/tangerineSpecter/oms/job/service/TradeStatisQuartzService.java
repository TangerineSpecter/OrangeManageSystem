package com.tangerinespecter.oms.job.service;

import cn.hutool.core.convert.Convert;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeStatisQuartzService {

    @Resource
    private ITradeStatisService tradeStatisService;

    /**
     * 统计往前天数
     *
     * @param param 天数，不传默认最近30天。
     */
    public void runData(String param) {
        log.info("[执行交易统计数据写入任务]，参数：" + param);
        tradeStatisService.statisTradeData(Convert.toInt(param), null);
        log.info("[交易统计任务执行完毕]");
    }
}
