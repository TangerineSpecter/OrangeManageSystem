package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.service.FundDataQuartzService;
import com.tangerinespecter.oms.job.service.WallPageQuartzService;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * 基金数据任务
 *
 * @author 丢失的橘子
 * @version 0.5.1
 * @date 2022年10月15日16:12:36
 */
public class FundDataQuartz extends QuartzJobBean {

    @Resource
    private FundDataQuartzService fundDataQuartzService;

    @Override
    protected void executeInternal(@NotNull JobExecutionContext jobExecutionContext) {
        fundDataQuartzService.runData();
    }
}
