package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.mapper.BaseMapperX;
import com.tangerinespecter.oms.common.mapper.QueryWrapperX;
import com.tangerinespecter.oms.system.domain.entity.SystemScheduledTask;

/**
 * <p>
 * 定时任务表 Mapper 接口
 * </p>
 *
 * @author 丢失的橘子
 * @since 2023年05月09日15:04:58
 */
public interface SystemScheduledTaskMapper extends BaseMapperX<SystemScheduledTask> {

    /**
     * 根据classPath路径获取task信息
     *
     * @param classPath 类路径
     * @return task信息
     */
    default SystemScheduledTask selectOneByClassPath(String classPath) {
        return selectOne(new QueryWrapperX<SystemScheduledTask>().eq("class_path", classPath));
    }

    /**
     * 根据类路径更新执行结果
     *
     * @param classPath 任务类路径
     * @param result    执行结果
     * @param timestamp 执行耗时
     */
    default void updateByClassPath(String classPath, Integer result, String timestamp) {
        update(new UpdateWrapper<SystemScheduledTask>().set("result", result).set("timestamp", timestamp)
                .eq("is_del", GlobalBoolEnum.FALSE.getValue()).eq("class_path", classPath));
    }
}
