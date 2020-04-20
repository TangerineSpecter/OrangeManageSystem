package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IRoleManageService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色管理控制
 *
 * @author TangerineSpecter
 * @date 2019年09月19日12:51:14
 */
@RestController
@RequestMapping("/system/role")
public class RoleManageController {

    @Resource
    private PageResultService pageResultService;
    @Resource
    private IRoleManageService roleManageService;

    /**
     * 角色管理
     */
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemRolePageKey, "system/roleManage");
    }

    /**
     * 角色列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult listInfo(SystemRoleQueryObject qo) {
        return roleManageService.querySystemRoleList(qo);
    }
}
