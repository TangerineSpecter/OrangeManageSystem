package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.schedule.AbstractJob;
import com.tangerinespecter.oms.job.service.FundDataQuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 基金数据任务
 *
 * @author 丢失的橘子
 * @version 0.5.1
 * @date 2022年10月15日16:12:36
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FundDataQuartz extends AbstractJob {

    private final FundDataQuartzService fundDataQuartzService;

    private static final String JOB_NAME = "基金数据任务";
    /**
     * 基金数据定时任务执行时间
     * 默认：每天23点执行一次
     */
    private static final String JOB_CRON = "0 0 23 * * ?";

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
        executorService.execute(fundDataQuartzService::runData);
    }
}
