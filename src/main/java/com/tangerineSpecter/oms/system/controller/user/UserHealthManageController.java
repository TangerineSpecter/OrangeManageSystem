package com.tangerinespecter.oms.system.controller.user;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.user.UserHealthInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.user.IUserHealthManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * 用户健康管理控制
 *
 * @author TangerineSpecter
 * @version 0.3.0
 * @date 2020年05月09日09:52:07
 */
@RestController
@Api(tags = "用户健康管理接口")
@RequestMapping("/user/health")
public class UserHealthManageController {

    @Resource
    private PageResultService pageResultService;
    @Resource
    private IUserHealthManageService userHealthManageService;

    /**
     * 健康管理页面
     */
    @ApiOperation(value = "健康管理页面")
    @RequiresPermissions("user:health:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String userHealthPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getUserHealthPageKey, "user/healthManage");
    }

    /**
     * 健康管理列表
     */
    @ApiOperation(value = "健康管理列表")
    @GetMapping("/list")
    public ServiceResult userHealthPage(Model model, UserHealthQueryObject qo) {
        return userHealthManageService.queryForPage(model, qo);
    }

    /**
     * 新增健康数据
     */
    @ApiOperation(value = "新增健康信息")
    @PostMapping("/insert")
    @LoggerInfo(value = "新增健康信息", event = LogOperation.EVENT_ADD)
    public ServiceResult insert(@RequestParam("type") @ApiParam("0：今天的记录，1：昨天的记录") Integer type) {
        return userHealthManageService.insert(type);
    }

    /**
     * 编辑健康数据
     */
    @ApiOperation(value = "更新健康信息")
    @PutMapping("/update")
    @LoggerInfo(value = "更新健康信息", event = LogOperation.EVENT_UPDATE)
    public ServiceResult update(@Validated @RequestBody UserHealthInfoVo data) {
        return userHealthManageService.update(data);
    }

    /**
     * 删除健康数据
     */
    @ApiOperation(value = "删除健康信息")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除健康信息", event = LogOperation.EVENT_DELETE)
    public ServiceResult delete(@NotNull(message = "id不能为空") @PathVariable("id") Long id) {
        return userHealthManageService.delete(id);
    }
}
