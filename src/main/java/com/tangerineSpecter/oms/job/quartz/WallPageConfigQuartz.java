package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.schedule.AbstractJob;
import com.tangerinespecter.oms.job.service.WallPageQuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 每日壁纸任务
 *
 * @author 丢失的橘子
 * @version 0.5.0
 * @date 2022年01月06日19:27:35
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WallPageConfigQuartz extends AbstractJob {

    private final WallPageQuartzService wallPageQuartzService;
    private static final String JOB_NAME = "微软每日壁纸任务";
    /**
     * 微软壁纸定时任务执行时间
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
        executorService.execute(wallPageQuartzService::runData);
    }
}
