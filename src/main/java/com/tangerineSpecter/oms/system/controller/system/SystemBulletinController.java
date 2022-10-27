package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemBulletinInfoVo;
import com.tangerinespecter.oms.system.domain.entity.SystemBulletin;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.ISystemBulletinService;
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
import javax.validation.constraints.NotNull;

/**
 * 系统公告管理
 *
 * @author TangerineSpecter
 * @version 0.3.0
 * @date 2020年05月08日22:07:42
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "系统公告接口")
@RequestMapping("/system/bulletin")
public class SystemBulletinController {

    private final PageResultService pageResultService;
    private final ISystemBulletinService systemBulletinService;

    @ApiOperation("系统公告页面")
    @RequiresPermissions("system:bulletin:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String bulletinPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemBulletinPageKey, "system/systemBulletin");
    }

    @ApiOperation("添加编辑公告页面")
    @GetMapping("/addPage")
    public ModelAndView addBulletinPage(Model model) {
        return ServiceResult.jumpPage("system/addEditBulletin");
    }

    @ApiOperation("系统公告列表")
    @PostMapping("/list")
    public PageInfo<SystemBulletin> bulletinPage(@RequestBody QueryObject<SystemBulletinQueryObject> qo) {
        return systemBulletinService.queryForPage(qo);
    }

    @ApiOperation("新增系统公告")
    @PostMapping("/insert")
    @LoggerInfo(value = "新增公告", event = LogOperation.EVENT_ADD)
    public void insert(@Validated @RequestBody SystemBulletinInfoVo data) {
        systemBulletinService.insert(data);
    }

    @ApiOperation("编辑系统公告")
    @PutMapping("/update")
    @LoggerInfo(value = "更新公告", event = LogOperation.EVENT_UPDATE)
    public void update(@Validated(Update.class) @RequestBody SystemBulletinInfoVo param) {
        systemBulletinService.update(param);
    }

    @ApiOperation("删除系统公告")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除公告", event = LogOperation.EVENT_DELETE)
    public void delete(@NotNull(message = "id不能为空") @PathVariable("id") Long id) {
        systemBulletinService.delete(id);
    }

    @ApiOperation("置顶系统公告")
    @PutMapping("/top")
    @LoggerInfo(value = "置顶公告", event = LogOperation.EVENT_UPDATE)
    public void topInfo(@Validated(Update.class) @RequestBody SystemBulletinInfoVo param) {
        systemBulletinService.topInfo(param.getId());
    }
}
