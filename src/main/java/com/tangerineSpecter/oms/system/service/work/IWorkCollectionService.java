package com.tangerinespecter.oms.system.service.work;

import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.work.WorkCollectionInfoVo;
import org.springframework.ui.Model;

public interface IWorkCollectionService {

    /**
     * 分页查询
     */
    ServiceResult queryForPage(Model model, WorkCollectionQueryObject qo);

    /**
     * 新增收藏
     */
    ServiceResult insert(WorkCollectionInfoVo data);

    /**
     * 更新收藏
     */
    ServiceResult update(WorkCollectionInfoVo data);

    /**
     * 删除收藏
     */
    ServiceResult delete(Long id);

}
