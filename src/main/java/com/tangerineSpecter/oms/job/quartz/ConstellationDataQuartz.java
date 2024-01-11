package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.schedule.AbstractJob;
import com.tangerinespecter.oms.job.service.ConstellationQuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 星座数据任务
 *
 * @author TangerineSpecter
 * @version v0.0.4
 * @Date 2019年1月6日
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConstellationDataQuartz extends AbstractJob {

    private final ConstellationQuartzService constellationQuartzService;
    private static final String JOB_NAME = "星座数据定时任务";
    /**
     * 星座定时任务执行时间
     * 默认：每6小时执行一次
     */
    private static final String JOB_CRON = "0 0 0/6 * * ?";

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
        constellationQuartzService.runData();
    }
}
