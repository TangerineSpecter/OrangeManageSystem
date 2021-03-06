package com.tangerinespecter.oms.system.controller.user;

import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日记管理
 *
 * @version 0.3.3
 * @date 2020年05月14日09:28:49
 */
@RestController
@RequestMapping("/user/diary")
public class DiaryManageController {

    @Resource
    private PageResultService pageResultService;

    /**
     * 日记管理页面
     */
    @ResponseBody
    @RequiresPermissions("user:diary:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String userHealthPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getUserDiaryPageKey, "user/diaryManage");
    }
}
