package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemRoleListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.domain.vo.system.SystemRoleInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IRoleManageService;
import com.tangerinespecter.oms.system.valid.Insert;
import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 角色管理控制
 *
 * @author TangerineSpecter
 * @date 2019年09月19日12:51:14
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "角色管理接口")
@RequestMapping("/system/role")
public class RoleManageController {

    private final PageResultService pageResultService;
    private final IRoleManageService roleManageService;

    @ApiOperation("角色管理页面")
    @RequiresPermissions("system:role:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemRolePageKey, "system/roleManage");
    }

    @ApiOperation("添加编辑角色页面")
    @GetMapping("/addPage")
    public ModelAndView addAuthorizePage(Model model) {
        return ServiceResult.jumpPage("system/roleAuthorize");
    }

    @ApiOperation("角色管理列表")
    @PostMapping("/list")
    public PageInfo<SystemRoleListDto> listInfo(@RequestBody QueryObject<SystemRoleQueryObject> qo) {
        return roleManageService.queryForPage(qo);
    }

    @ApiOperation("添加角色")
    @PostMapping("/insert")
    public void insert(@Validated(Insert.class) @RequestBody SystemRoleInfoVo param) {
        roleManageService.insert(param.getName());
    }

    @ApiOperation("角色授权")
    @PutMapping("/authorize")
    public void authorize(@Validated @RequestBody SystemRoleInfoVo param) {
        roleManageService.authorize(param);
    }

    @ApiOperation("更新角色状态")
    @PutMapping("/update-status")
    public void updateStatus(@Validated(Update.class) @RequestBody SystemRoleInfoVo param) {
        roleManageService.updateStatus(param.getId());
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        roleManageService.delete(id);
    }

    @ApiOperation("获取角色权限列表")
    @GetMapping("/get-permission")
    public Set<SystemPermission> getRolePermission(@RequestParam("id") Long roleId) {
        return roleManageService.getRolePermission(roleId);
    }
}
