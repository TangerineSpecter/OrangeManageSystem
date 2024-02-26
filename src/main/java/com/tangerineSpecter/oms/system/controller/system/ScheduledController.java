package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.SystemScheduledQueryObject;
import com.tangerinespecter.oms.common.query.SystemTokenQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemScheduledTask;
import com.tangerinespecter.oms.system.domain.vo.base.EnableParam;
import com.tangerinespecter.oms.system.domain.vo.base.IdParamVo;
import com.tangerinespecter.oms.system.domain.vo.system.ExecuteJobVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemScheduledVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IScheduledManageService;
import com.tangerinespecter.oms.system.service.system.ITokenManageService;
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

/**
 * 定时任务管理控制
 *
 * @author 丢失的橘子
 * @date 2023年05月09日14:32:11
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "定时任务管理")
@RequestMapping("/system/scheduled")
public class ScheduledController {

    private final PageResultService pageResultService;
    private final IScheduledManageService scheduledManageService;
    private final ITokenManageService tokenManageService;

    @ApiOperation("定时任务页面")
    @RequiresPermissions("system:scheduled:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.SYSTEM_SCHEDULED_PAGE_KEY, "system/scheduledManage");
    }

    @ApiOperation("添加编辑定时任务页面")
    @GetMapping("/addPage")
    public ModelAndView addAuthorizePage(Model model) {
        model.addAttribute("botList", tokenManageService.list(new SystemTokenQueryObject(0)));
        return ServiceResult.jumpPage("system/addEditSystemScheduled");
    }

    @ApiOperation("定时任务管理列表")
    @PostMapping("/list")
    public PageInfo<SystemScheduledTask> listInfo(@RequestBody QueryObject<SystemScheduledQueryObject> qo) {
        return scheduledManageService.queryForPage(qo);
    }

    @ApiOperation("新增定时任务")
    @PostMapping("/insert")
    @LoggerInfo(value = "新增定时任务", event = LogOperation.EVENT_ADD)
    public void insert(@Validated @RequestBody SystemScheduledVo param) {
        scheduledManageService.insert(param);
    }

    @ApiOperation("编辑定时任务")
    @PutMapping("/update")
    @LoggerInfo(value = "更新定时任务", event = LogOperation.EVENT_UPDATE)
    public void update(@Validated(Update.class) @RequestBody SystemScheduledVo param) {
        scheduledManageService.update(param);
    }

    @ApiOperation("立刻执行")
    @PostMapping("/execute")
    @LoggerInfo(value = "执行定时任务", event = LogOperation.EVENT_USE)
    public void executeJob(@RequestBody ExecuteJobVo param) {
        scheduledManageService.executeJob(param);
    }

    @ApiOperation("启用|停用定时任务")
    @PutMapping("/enable")
    @LoggerInfo(value = "启用|停用定时任务", event = LogOperation.EVENT_UPDATE)
    public void enable(@RequestBody EnableParam param) {
        scheduledManageService.enable(param.getId(), param.isEnable());
    }

    @ApiOperation("删除定时任务")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除定时任务", event = LogOperation.EVENT_DELETE)
    public void delete(@PathVariable("id") Long id) {
        scheduledManageService.delete(id);
    }

}
