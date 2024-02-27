package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemScheduledQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemScheduledTask;
import com.tangerinespecter.oms.system.domain.vo.system.ExecuteJobVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemScheduledVo;
import com.tangerinespecter.oms.system.service.BaseService;

public interface IScheduledManageService extends BaseService<SystemScheduledQueryObject, SystemScheduledTask> {

    /**
     * 新增定时任务
     *
     * @param param 参数
     */
    void insert(SystemScheduledVo param);

    /**
     * 编辑定时任务
     *
     * @param param 参数
     */
    void update(SystemScheduledVo param);

    /**
     * 启用|停用定时任务
     *
     * @param id     数据id
     * @param enable true：启用
     */
    void enable(Long id, boolean enable);

    /**
     * 删除定时任务
     *
     * @param id 数据id
     */
    void delete(Long id);

    /**
     * 立刻执行job
     *
     * @param param 参数
     */
    void executeJob(ExecuteJobVo param);
}
