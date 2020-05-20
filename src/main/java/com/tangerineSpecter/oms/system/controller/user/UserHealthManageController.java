package com.tangerinespecter.oms.system.controller.user;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.user.UserHealthInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.user.IUserHealthManageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 用户健康管理控制
 *
 * @author TangerineSpecter
 * @version 0.3.0
 * @date 2020年05月09日09:52:07
 */
@Controller
@RequestMapping("/user/health")
public class UserHealthManageController {

    @Resource
    private PageResultService pageResultService;
    @Resource
    private IUserHealthManageService userHealthManageService;

    /**
     * 健康管理页面
     */
    @ResponseBody
    @RequiresPermissions("user:health:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String userHealthPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getUserHealthPageKey, "user/healthManage");
    }

    /**
     * 健康管理列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult userHealthPage(Model model, UserHealthQueryObject qo) {
        return userHealthManageService.queryForPage(model, qo);
    }

    /**
     * 新增健康数据
     */
    @ResponseBody
    @RequestMapping("/insert")
    @LoggerInfo(value = "新增健康信息", event = LogOperation.EVENT_ADD)
    public ServiceResult insert(@RequestParam("type") Integer type) {
        return userHealthManageService.insert(type);
    }

    /**
     * 编辑健康数据
     */
    @ResponseBody
    @RequestMapping("/update")
    @LoggerInfo(value = "更新健康信息", event = LogOperation.EVENT_UPDATE)
    public ServiceResult update(@Valid UserHealthInfoVo data) {
        return userHealthManageService.update(data);
    }

    /**
     * 删除健康数据
     */
    @ResponseBody
    @RequestMapping("/delete")
    @LoggerInfo(value = "删除健康信息", event = LogOperation.EVENT_DELETE)
    public ServiceResult delete(Long id) {
        return userHealthManageService.delete(id);
    }
}
