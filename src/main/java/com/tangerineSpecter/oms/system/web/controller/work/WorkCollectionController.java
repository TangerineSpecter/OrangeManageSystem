package com.tangerinespecter.oms.system.web.controller.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.dao.entity.WorkCollection;
import com.tangerinespecter.oms.system.service.work.WorkCollectionService;

/**
 * 收藏信息相关控制
 *
 * @author tangerinespecter
 * @version v0.1.2
 * @Date 2019年1月22日
 */
@Controller
public class WorkCollectionController {

    @Autowired
    private WorkCollectionService workCollectionService;

    /**
     * 收藏页面
     */
    @RequestMapping(ServiceKey.Work.COLLECTION_PAGE_LIST)
    public String constellationPage(Model model, WorkCollectionQueryObject qo) {
        workCollectionService.queryForPage(model, qo);
        return "work/collection";
    }

    /**
     * 新增收藏
     */
    @ResponseBody
    @RequestMapping(ServiceKey.Work.COLLECTION_ADD)
    public ServiceResult insert(WorkCollection data) {
        return workCollectionService.insert(data);
    }

    /**
     * 删除收藏
     */
    @ResponseBody
    @RequestMapping(ServiceKey.Work.COLLECTION_DELETE)
    public ServiceResult delete(Long id) {
        return workCollectionService.delete(id);
    }
}
