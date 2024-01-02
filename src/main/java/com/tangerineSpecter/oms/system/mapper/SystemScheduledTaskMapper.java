package com.tangerinespecter.oms.system.mapper;

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
        return selectOne(new QueryWrapperX<SystemScheduledTask>()
                .eq("class_path", classPath));
    }
}
