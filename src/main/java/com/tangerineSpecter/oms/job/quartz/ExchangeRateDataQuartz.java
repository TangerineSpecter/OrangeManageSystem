package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.schedule.AbstractJob;
import com.tangerinespecter.oms.job.service.ExchangeRateQuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 汇率定时任务
 *
 * @author TangerineSpecter
 * @version v0.5.0
 * @date 2022年05月26日17:57:45
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateDataQuartz extends AbstractJob {

    private final ExchangeRateQuartzService exchangeRateQuartzService;

    private static final String JOB_NAME = "汇率定时任务";
    /**
     * 汇率定时任务执行时间
     * 默认：每3小时执行一次
     */
    private static final String JOB_CRON = "0 0 0/3 * * ?";

    @Override
    public String getName() {
        return JOB_NAME;
    }

    @Override
    public void setMsgType(Integer msgType) {

    }

    @Override
    public void setExtraInfo(String extraInfo) {

    }

    @Override
    public void execute() {
        exchangeRateQuartzService.runData();
    }
}
