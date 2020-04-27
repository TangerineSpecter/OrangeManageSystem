package com.tangerinespecter.oms.system.controller.work;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.work.WorkCollectionInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.work.IWorkCollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 收藏信息相关控制
 *
 * @author tangerinespecter
 * @version v0.1.2
 * @Date 2019年1月22日
 */
@Controller
@RequestMapping("/work/collection")
public class WorkCollectionController {

    @Resource
    private IWorkCollectionService workCollectionService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 收藏页面
     */
    @ResponseBody
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String collectionPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getWorkCollectionPageKey, "work/collection");
    }

    /**
     * 添加页面
     */
    @RequestMapping("/addPage")
    public String addCollectionPage(Model model) {
        return "work/addEditCollection";
    }

    /**
     * 收藏列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult constellationPage(Model model, WorkCollectionQueryObject qo) {
        return workCollectionService.queryForPage(model, qo);
    }

    /**
     * 新增收藏
     */
    @ResponseBody
    @RequestMapping("/insert")
    @LoggerInfo(value = "新增收藏", event = LogOperation.EVENT_ADD)
    public ServiceResult insert(@Valid WorkCollectionInfoVo data) {
        return workCollectionService.insert(data);
    }

    /**
     * 编辑收藏
     */
    @ResponseBody
    @RequestMapping("/update")
    @LoggerInfo(value = "更新收藏", event = LogOperation.EVENT_UPDATE)
    public ServiceResult update(@Valid WorkCollectionInfoVo data) {
        return workCollectionService.update(data);
    }

    /**
     * 删除收藏
     */
    @ResponseBody
    @RequestMapping("/delete")
    @LoggerInfo(value = "删除收藏", event = LogOperation.EVENT_DELETE)
    public ServiceResult delete(Long id) {
        return workCollectionService.delete(id);
    }
}
