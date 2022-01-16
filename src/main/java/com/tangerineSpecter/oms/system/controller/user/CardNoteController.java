package com.tangerinespecter.oms.system.controller.user;

import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 卡片笔记
 *
 * @author 丢失的橘子
 * @date 2022年1月16日 21:56:51
 */
@Api(tags = "卡片笔记模块")
@RestController
@RequestMapping("/user/card-note")
public class CardNoteController {

    @Resource
    private PageResultService pageResultService;

    /**
     * 卡片日记界面
     */
    @ApiOperation(value = "卡片日记界面")
    @RequiresPermissions("user:card-note:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String cardNotePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getCardNotePageKey, "user/cardNoteManage");
    }


}
