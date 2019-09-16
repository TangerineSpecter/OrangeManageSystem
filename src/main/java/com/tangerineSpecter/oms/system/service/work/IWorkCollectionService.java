package com.tangerinespecter.oms.system.service.work;

import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import org.springframework.ui.Model;

public interface IWorkCollectionService {

    /**
     * 分页查询
     */
    void queryForPage(Model model, WorkCollectionQueryObject qo);

    /**
     * 新增收藏
     */
    ServiceResult insert(WorkCollection data);

    /**
     * 删除收藏
     */
    ServiceResult delete(Long id);
}
