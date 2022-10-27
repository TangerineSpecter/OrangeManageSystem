package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.SystemPermissionQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IPermissionManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限管理控制
 *
 * @author TangerineSpecter
 * @version 0.3.3
 * @date 2020年05月19日12:58:32
 */
@RestController
@ReWriteBody
@RequiredArgsConstructor
@Api(tags = "权限管理接口")
@RequestMapping("/system/permission")
public class PermissionManageController {

    private final PageResultService pageResultService;
    private final IPermissionManageService permissionManageService;

    @ApiOperation("权限管理页面")
    @RequiresPermissions("system:permission:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemPermissionPageKey, "system/permissionManage");
    }

    @ApiOperation("权限管理列表")
    @AccessLimit(maxCount = 10)
    @PostMapping("/list")
    public PageInfo<SystemPermission> listInfo(@RequestBody QueryObject<SystemPermissionQueryObject> qo) {
        return permissionManageService.queryForPage(qo);
    }

    @ApiOperation("权限初始化")
    @AccessLimit(maxCount = 3)
    @PostMapping("/init")
    public void init() {
        permissionManageService.init();
    }
}
