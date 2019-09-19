package com.tangerinespecter.oms.system.controller.work;

import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.work.IWorkCollectionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 收藏信息相关控制
 *
 * @author tangerinespecter
 * @version v0.1.2
 * @Date 2019年1月22日
 */
@RestController
@RequestMapping("/work/collection")
public class WorkCollectionController {

    @Resource
    private IWorkCollectionService workCollectionService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 收藏页面
     */
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String constellationPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getWorkCollectionPageKey, "work/collection");
    }

    /**
     * 收藏列表
     */
    @RequestMapping("/list")
    public ServiceResult constellationPage(Model model, WorkCollectionQueryObject qo) {
        return workCollectionService.queryForPage(model, qo);
    }

    /**
     * 新增收藏
     */
    @RequestMapping("/insert")
    public ServiceResult insert(WorkCollection data) {
        return workCollectionService.insert(data);
    }

    /**
     * 删除收藏
     */
    @RequestMapping("/delete")
    public ServiceResult delete(Long id) {
        return workCollectionService.delete(id);
    }
}
