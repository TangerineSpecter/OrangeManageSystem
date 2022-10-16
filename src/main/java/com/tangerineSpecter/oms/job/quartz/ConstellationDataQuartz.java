package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.service.ConstellationQuartzService;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * 星座数据任务
 *
 * @author TangerineSpecter
 * @version v0.0.4
 * @Date 2019年1月6日
 */
public class ConstellationDataQuartz extends QuartzJobBean {

    @Resource
    private ConstellationQuartzService constellationQuartzService;

    @Override
    protected void executeInternal(@NotNull JobExecutionContext jobExecutionContext) {
        constellationQuartzService.runData();
    }

}
