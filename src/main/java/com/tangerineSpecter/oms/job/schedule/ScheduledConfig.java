package com.tangerinespecter.oms.job.schedule;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.query.SystemScheduledQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemScheduledTask;
import com.tangerinespecter.oms.system.service.system.IScheduledManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Scheduled定时任务配置
 *
 * @author 丢失的橘子
 * @date 2023年05月09日15:17:59
 */
@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledConfig implements SchedulingConfigurer {

    private final ApplicationContext context;
    private final IScheduledManageService scheduledManageService;

    private static final List<SystemScheduledTask> SCHEDULED_LIST = CollUtil.newArrayList();

    @PostConstruct
    public void init() {
        //数据库查询到所有的定时任务
        CollUtil.addAll(SCHEDULED_LIST, scheduledManageService.list(new SystemScheduledQueryObject()));
        log.info("[定时任务初始化完毕]，数量：" + CollUtil.size(SCHEDULED_LIST));
    }

    @Override
    public void configureTasks(@NotNull ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //创建定时任务
        for (SystemScheduledTask scheduled : SCHEDULED_LIST) {
            AbstractJob task = null;
            try {
                task = (AbstractJob) context.getBean(Class.forName(scheduled.getClassPath()));
            } catch (ClassNotFoundException e) {
                log.error("[创建定时任务失败，无法找到指定类名[{}]，异常信息：" + e, e.getMessage());
            }
            if (task != null) {
                task.createJob(scheduled.getCron());
                task.setMsgType(scheduled.getMsgType());
                task.setExtraInfo(scheduled.getExtraInfo());
            }
        }
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
