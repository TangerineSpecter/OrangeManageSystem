package com.tangerinespecter.oms.system.service.work;

import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.system.domain.dto.work.WorkCollectionInfoVo;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import com.tangerinespecter.oms.system.service.BaseService;

public interface IWorkCollectionService extends BaseService<WorkCollectionQueryObject, WorkCollection> {

    /**
     * 新增收藏
     *
     * @param data 添加数据
     */
    void insert(WorkCollectionInfoVo data);

    /**
     * 更新收藏
     *
     * @param data 更新数据
     */
    void update(WorkCollectionInfoVo data);

    /**
     * 删除收藏
     *
     * @param id 收藏id
     */
    void delete(Long id);

}
