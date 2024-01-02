package com.tangerinespecter.oms.job.schedule;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledFuture;

/**
 * 任务抽象类
 *
 * @author 丢失的橘子
 */
@Slf4j
public abstract class AbstractJob implements Runnable {

    private ScheduledFuture<?> scheduledFuture;

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 获取任务名称
     *
     * @return 任务名称
     */
    public abstract String getName();

    /**
     * 设置消息类型
     *
     * @param msgType 消息类型，参照：BotMsgTypeEnum
     */
    public abstract void setMsgType(Integer msgType);

    /**
     * 设置额外信息
     *
     * @param extraInfo 信息
     */
    public abstract void setExtraInfo(String extraInfo);

    /**
     * 开始执行任务
     */
    public abstract void execute();


    /**
     * 创建定时任务
     *
     * @param cron 时间表达式
     */
    public void createJob(String cron) {
        if (StrUtil.isEmpty(cron)) {
            log.warn("定时任务[{}]执行时间为空，跳过创建", this.getName());
            return;
        }
        try {
            //取消之前的定时任务
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }
            scheduledFuture = threadPoolTaskScheduler.schedule(this, new CronTrigger(cron));
        } catch (Exception e) {
            log.error("创建定时任务[{}]失败！，异常信息：[{}]，异常：" + e, this.getName(), e.getMessage());
        }
    }

    /**
     * 定时任务执行之前
     */
    public void begin() {
        log.info("------开始执行[{}]任务------", this.getName());
    }

    /**
     * 定时任务执行之后
     */
    public void end() {
        log.info("------结束执行[{}]任务------", this.getName());
    }


    /**
     * 取消执行任务
     */
    public void cancel() {
        try {
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }
            log.info("取消执行[{}]任务！", getName());
        } catch (Exception e) {
            log.error("取消执行[{}]失败 ！", getName());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        begin();
        execute();
        end();
    }
}
