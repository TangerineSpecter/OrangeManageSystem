package com.tangerinespecter.oms.system.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.domain.vo.system.SystemRoleInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IRoleManageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;

/**
 * 角色管理控制
 *
 * @author TangerineSpecter
 * @date 2019年09月19日12:51:14
 */
@Controller
@RequestMapping("/system/role")
public class RoleManageController {

    @Resource
    private PageResultService pageResultService;
    @Resource
    private IRoleManageService roleManageService;

    /**
     * 角色管理
     */
    @ResponseBody
    @SaCheckPermission("system:role:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemRolePageKey, "system/roleManage");
    }

    /**
     * 添加页面
     */
    @RequestMapping("/addPage")
    public String addAuthorizePage(Model model) {
        return "system/roleAuthorize";
    }

    /**
     * 角色列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult listInfo(SystemRoleQueryObject qo) {
        return roleManageService.querySystemRoleList(qo);
    }

    /**
     * 添加角色
     */
    @ResponseBody
    @RequestMapping("/insert")
    public ServiceResult insert(@RequestParam("name") String name) {
        return roleManageService.insert(name);
    }

    /**
     * 角色授权
     */
    @ResponseBody
    @RequestMapping("/authorize")
    public ServiceResult authorize(@Valid SystemRoleInfoVo vo) {
        return roleManageService.authorize(vo);
    }

    /**
     * 更新角色状态
     */
    @ResponseBody
    @RequestMapping("/update-status")
    public ServiceResult updateStatus(@RequestParam("id") Long id) {
        return roleManageService.updateStatus(id);
    }

    /**
     * 删除角色
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ServiceResult delete(@RequestParam("id") Long id) {
        return roleManageService.delete(id);
    }

    /**
     * 获取角色权限列表
     */
    @ResponseBody
    @RequestMapping("/get-permission")
    public Set<SystemPermission> getRolePermission(@RequestParam("id") Long roleId) {
        return roleManageService.getRolePermission(roleId);
    }
}
