package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.tangerinespecter.oms.common.config.ThreadPoolConfig;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.enums.ScheduledTypeEnum;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.mapper.QueryWrapperX;
import com.tangerinespecter.oms.common.query.SystemScheduledQueryObject;
import com.tangerinespecter.oms.common.redis.RedisKey;
import com.tangerinespecter.oms.job.schedule.AbstractJob;
import com.tangerinespecter.oms.system.convert.system.ScheduledConvert;
import com.tangerinespecter.oms.system.domain.entity.SystemScheduledTask;
import com.tangerinespecter.oms.system.domain.vo.system.ExecuteJobVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemScheduledVo;
import com.tangerinespecter.oms.system.mapper.SystemScheduledTaskMapper;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import com.tangerinespecter.oms.system.service.system.IScheduledManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * @author TangerineSpecter
 * 2023年05月15日11:11:04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledManageServiceImpl implements IScheduledManageService {

    private final SystemScheduledTaskMapper scheduledTaskMapper;
    private final ApplicationContext context;
    private final RedisHelper redisHelper;
    private final ThreadPoolConfig threadPoolConfig;

    @Override
    public List<SystemScheduledTask> list(SystemScheduledQueryObject qo) {
        return scheduledTaskMapper.selectList(new QueryWrapperX<SystemScheduledTask>()
            .likeIfPresent("name", qo.getName()).eqIfPresent("type", qo.getType()).eqIfPresent("status", qo.getStatus())
            .eq("is_del", GlobalBoolEnum.FALSE.getValue()));
    }

    /**
     * 处理定时任务（重置）
     *
     * @param classPath 类名
     * @param cron      时间公式
     */
    private void executeJob(String classPath, String cron) {
        AbstractJob job = null;
        try {
            job = (AbstractJob) context.getBean(Class.forName(classPath));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (job != null) {
            job.createJob(cron);
        }
    }

    /**
     * 取消定时任务
     *
     * @param classPath 类地址
     */
    private void cancelJob(String classPath) {
        AbstractJob job = null;
        try {
            job = (AbstractJob) context.getBean(Class.forName(classPath));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (job != null) {
            job.cancel();
        }
    }

    @Override
    public void insert(SystemScheduledVo param) {
        this.executeJob(param.getClassPath(), param.getCron());
        scheduledTaskMapper.insert(ScheduledConvert.INSTANCE.convert(param));
    }

    @Override
    public void update(SystemScheduledVo param) {
        this.executeJob(param.getClassPath(), param.getCron());
        scheduledTaskMapper.updateById(ScheduledConvert.INSTANCE.convert(param));
    }

    @Override
    public void enable(Long id, boolean enable) {
        SystemScheduledTask task = scheduledTaskMapper.selectById(id);
        if (enable) {
            this.executeJob(task.getClassPath(), task.getCron());
        } else {
            this.cancelJob(task.getClassPath());
        }
        task.setStatus(Convert.toInt(enable));
        scheduledTaskMapper.updateById(task);
    }

    @Override
    public void delete(Long id) {
        SystemScheduledTask task = scheduledTaskMapper.selectById(id);
        if (Objects.equals(ScheduledTypeEnum.DEFAULT.getValue(), task.getType())) {
            throw new BusinessException(RetCode.DEFAULT_TASK_NOT_DELETE);
        }
        this.cancelJob(task.getClassPath());
        scheduledTaskMapper.deleteById(id);
    }

    @Override
    public void executeJob(ExecuteJobVo vo) {
        final RedisKey redisKey = RedisKey.JOB_LOCK;

        if (!redisHelper.lock(redisKey, vo.getId(), "lock")) {
            throw new BusinessException(RetCode.TASK_EXECUTE_RUNNING);
        }

        final ExecutorService executorService = threadPoolConfig.getExecutorService();
        try {
            SystemScheduledTask systemScheduledTask = scheduledTaskMapper.selectById(vo.getId());
            AbstractJob job = (AbstractJob) context.getBean(Class.forName(systemScheduledTask.getClassPath()));
            job.setExtraInfo(StrUtil.isBlank(vo.getParam()) ? systemScheduledTask.getExtraInfo() : vo.getParam());
            executorService.execute(job);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            redisHelper.releaseLock(redisKey, vo.getId());
            throw new BusinessException(RetCode.TASK_EXECUTE_NOT_EXIST);
        } catch (Exception e) {
            e.printStackTrace();
            redisHelper.releaseLock(redisKey, vo.getId());
            throw new BusinessException(RetCode.TASK_EXECUTE_ERROR);
        } finally {
            executorService.shutdown();
        }
        redisHelper.releaseLock(redisKey, vo.getId());
    }

}
