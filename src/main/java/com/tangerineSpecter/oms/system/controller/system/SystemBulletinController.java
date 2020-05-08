package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemBulletinInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.ISystemBulletinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 系统公告管理
 *
 * @author TangerineSpecter
 * @version 0.3.0
 * @date 2020年05月08日22:07:42
 */
@Controller
@RequestMapping("/system/bulletin")
public class SystemBulletinController {

    @Resource
    private PageResultService pageResultService;
    @Resource
    private ISystemBulletinService systemBulletinService;

    /**
     * 收藏页面
     */
    @ResponseBody
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String collectionPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemBulletinPageKey, "system/systemBulletin");
    }

    /**
     * 添加页面
     */
    @RequestMapping("/addPage")
    public String addCollectionPage(Model model) {
        return "system/addEditBulletin";
    }

    /**
     * 收藏列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult constellationPage(Model model, SystemBulletinQueryObject qo) {
        return systemBulletinService.queryForPage(model, qo);
    }

    /**
     * 新增收藏
     */
    @ResponseBody
    @RequestMapping("/insert")
    @LoggerInfo(value = "新增公告", event = LogOperation.EVENT_ADD)
    public ServiceResult insert(@Valid SystemBulletinInfoVo data) {
        return systemBulletinService.insert(data);
    }

    /**
     * 编辑收藏
     */
    @ResponseBody
    @RequestMapping("/update")
    @LoggerInfo(value = "更新公告", event = LogOperation.EVENT_UPDATE)
    public ServiceResult update(@Valid SystemBulletinInfoVo data) {
        return systemBulletinService.update(data);
    }

    /**
     * 删除收藏
     */
    @ResponseBody
    @RequestMapping("/delete")
    @LoggerInfo(value = "删除公告", event = LogOperation.EVENT_DELETE)
    public ServiceResult delete(@RequestParam("id") Long id) {
        return systemBulletinService.delete(id);
    }

    /**
     * 置顶公告
     */
    @ResponseBody
    @RequestMapping("/top")
    @LoggerInfo(value = "置顶公告", event = LogOperation.EVENT_UPDATE)
    public ServiceResult topInfo(@RequestParam("id") Long id) {
        return systemBulletinService.topInfo(id);
    }
}
