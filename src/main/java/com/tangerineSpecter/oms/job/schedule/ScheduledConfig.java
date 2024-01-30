package com.tangerinespecter.oms.job.schedule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.util.ReflectUtil;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.enums.ScheduledTypeEnum;
import com.tangerinespecter.oms.common.query.SystemScheduledQueryObject;
import com.tangerinespecter.oms.common.redis.RedisKey;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemScheduledTask;
import com.tangerinespecter.oms.system.mapper.SystemScheduledTaskMapper;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
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
import java.util.Set;

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
    private final SystemScheduledTaskMapper scheduledTaskMapper;
    private final RedisHelper redisHelper;

    /**
     * 系统默认定时任务包路径
     */
    private static final String SYSTEM_DEFAULT_TASK_PACKAGE = "com.tangerinespecter.oms.job.quartz";

    private static final List<SystemScheduledTask> SCHEDULED_LIST = CollUtil.newArrayList();

    @PostConstruct
    public void init() {
        final SystemScheduledQueryObject queryObject = new SystemScheduledQueryObject();
        queryObject.setStatus(GlobalBoolEnum.TRUE.getValue());
        List<SystemScheduledTask> list = scheduledManageService.list(queryObject);
        CollUtil.addAll(SCHEDULED_LIST, this.initDefaultTask(list));
        //数据库查询到所有的定时任务
        CollUtil.addAll(SCHEDULED_LIST, list);
        //重置锁，避免重启导致锁未释放
        CollUtils.forEach(list, data -> redisHelper.releaseLock(RedisKey.JOB_LOCK, data.getId()));
        log.info("[初始化定时任务完毕]，数量：" + CollUtil.size(SCHEDULED_LIST));
    }

    /**
     * 初始化系统默认task
     *
     * @param list 已有任务列表
     * @return 新增任务列表
     */
    private List<SystemScheduledTask> initDefaultTask(List<SystemScheduledTask> list) {
        List<SystemScheduledTask> insertTask = CollUtil.newArrayList();
        final List<SystemScheduledTask> taskList = this.getDefaultTask();
        final List<String> dbClassPath = CollUtils.convertList(list, SystemScheduledTask::getClassPath);
        for (SystemScheduledTask task : taskList) {
            if (dbClassPath.contains(task.getClassPath())) {
                continue;
            }
            scheduledTaskMapper.insert(task);
            insertTask.add(task);
        }
        return insertTask;
    }

    /**
     * 系统默认定时任务信息
     *
     * @return 系统默认定时任务列表
     */
    public List<SystemScheduledTask> getDefaultTask() {
        final List<SystemScheduledTask> result = CollUtil.newArrayList();
        final Set<Class<?>> classes = ClassScanner.scanPackage(SYSTEM_DEFAULT_TASK_PACKAGE);
        for (Class<?> clazz : classes) {
            final Object jobName = ReflectUtil.getFieldValue(clazz, "JOB_NAME");
            final Object jobCron = ReflectUtil.getFieldValue(clazz, "JOB_CRON");
            SystemScheduledTask task = new SystemScheduledTask();
            task.setClassPath(clazz.getName());
            task.setCron(Convert.toStr(jobCron));
            task.setName(Convert.toStr(jobName));
            task.setType(ScheduledTypeEnum.DEFAULT.getValue());
            result.add(task);
        }
        return result;
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
