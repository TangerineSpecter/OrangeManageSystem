package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.SystemTokenQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemTokenVo;
import com.tangerinespecter.oms.system.domain.entity.SystemToken;
import com.tangerinespecter.oms.system.service.page.PageResultService;
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
 * @author 丢失的橘子
 * @version v0.5.2
 * @Date 2023年05月08日11:28:51
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "令牌管理接口")
@RequestMapping("/system/token")
public class TokenController {

    private final PageResultService pageResultService;
    private final ITokenManageService tokenManageService;


    @ApiOperation("令牌管理页面")
    @RequiresPermissions("system:token:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemTokenPageKey, "system/tokenManage");
    }

    @ApiOperation("添加编辑令牌页面")
    @GetMapping("/addPage")
    public ModelAndView addAuthorizePage(Model model) {
        return ServiceResult.jumpPage("system/addEditSystemToken");
    }

    @ApiOperation("令牌管理列表")
    @PostMapping("/list")
    public PageInfo<SystemToken> listInfo(@RequestBody QueryObject<SystemTokenQueryObject> qo) {
        return tokenManageService.queryForPage(qo);
    }

    @ApiOperation("新增令牌")
    @PostMapping("/insert")
    @LoggerInfo(value = "新增公告", event = LogOperation.EVENT_ADD)
    public void insert(@Validated @RequestBody SystemTokenVo param) {
        tokenManageService.insert(param);
    }

    @ApiOperation("编辑令牌")
    @PutMapping("/update")
    @LoggerInfo(value = "更新公告", event = LogOperation.EVENT_UPDATE)
    public void update(@Validated(Update.class) @RequestBody SystemTokenVo param) {
        tokenManageService.update(param);
    }

    @ApiOperation("删除令牌")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除令牌", event = LogOperation.EVENT_DELETE)
    public void delete(@PathVariable("id") Long id) {
        tokenManageService.delete(id);
    }
}
