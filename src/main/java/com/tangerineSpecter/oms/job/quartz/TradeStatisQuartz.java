package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.schedule.AbstractJob;
import com.tangerinespecter.oms.job.service.TradeStatisQuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 交易记录统计
 *
 * @author 丢失的橘子
 * @version 0.5.3
 * @date 2024年01月12日18:09:53
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TradeStatisQuartz extends AbstractJob {

    private final TradeStatisQuartzService tradeStatisQuartzService;
    private static final String JOB_NAME = "交易记录统计任务";
    /**
     * 交易记录统计任务执行时间
     * 默认：每周六执行一次
     */
    private static final String JOB_CRON = "0 0 2 ? * 6";

    private String extraInfo = "30";

    @Override
    public String getName() {
        return JOB_NAME;
    }

    @Override
    public void setMsgType(Integer msgType) {

    }

    @Override
    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public void execute() {
        tradeStatisQuartzService.runData(extraInfo);
    }
}
