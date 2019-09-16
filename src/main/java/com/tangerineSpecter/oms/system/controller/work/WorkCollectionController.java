package com.tangerinespecter.oms.system.controller.work;

import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import com.tangerinespecter.oms.system.service.work.IWorkCollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 收藏信息相关控制
 *
 * @author tangerinespecter
 * @version v0.1.2
 * @Date 2019年1月22日
 */
@Controller
public class WorkCollectionController {

    @Resource
    private IWorkCollectionService workCollectionService;

    /**
     * 收藏页面
     */
    @RequestMapping("/collection")
    public String constellationPage(Model model, WorkCollectionQueryObject qo) {
        workCollectionService.queryForPage(model, qo);
        return "work/collection";
    }

    /**
     * 新增收藏
     */
    @ResponseBody
    @RequestMapping("/collection/add")
    public ServiceResult insert(WorkCollection data) {
        return workCollectionService.insert(data);
    }

    /**
     * 删除收藏
     */
    @ResponseBody
    @RequestMapping("/collection/delete")
    public ServiceResult delete(Long id) {
        return workCollectionService.delete(id);
    }
}
