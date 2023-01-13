package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.service.ExchangeRateQuartzService;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * 汇率定时任务
 *
 * @author TangerineSpecter
 * @version v0.5.0
 * @date 2022年05月26日17:57:45
 */
public class ExchangeRateDataQuartz extends QuartzJobBean {

    @Resource
    private ExchangeRateQuartzService exchangeRateQuartzService;

    @Override
    protected void executeInternal(@NotNull JobExecutionContext jobExecutionContext) {
        exchangeRateQuartzService.runData();
    }

}
