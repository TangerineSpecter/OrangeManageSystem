package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IMenuSettingService;
import com.tangerinespecter.oms.system.valid.IdParam;
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
import java.util.List;

/**
 * 菜单设置控制
 *
 * @author TangerineSpecter
 * @date 2019年09月01日18:40:56
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "菜单管理接口")
@RequestMapping("/system/menu")
public class MenuSettingController {

    private final IMenuSettingService menuSettingService;
    private final PageResultService pageResultService;

    @ApiOperation("菜单管理页面")
    @RequiresPermissions("system:menu:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemMenuPageKey, "system/menuSetting");
    }

    @ApiOperation("添加编辑菜单页面")
    @GetMapping("/addPage")
    public ModelAndView addMenuPage(Model model) {
        model.addAttribute("menuList", menuSettingService.listInfo());
        return ServiceResult.jumpPage("system/addEditMenuSetting");
    }

    @ApiOperation("菜单列表")
    @GetMapping("/list")
    public PageInfo<SystemMenu> listInfo() {
        return menuSettingService.listInfo();
    }

    @ApiOperation("新增菜单")
    @PostMapping("/insert")
    @LoggerInfo(value = "添加菜单", event = LogOperation.EVENT_ADD)
    public void insertInfo(@Validated(Insert.class) @RequestBody SystemMenuInfoVo vo) {
        menuSettingService.insertInfo(vo);
    }

    @ApiOperation("编辑菜单")
    @PutMapping("/update")
    @LoggerInfo(value = "编辑菜单", event = LogOperation.EVENT_UPDATE)
    public void updateInfo(@Validated(Update.class) @RequestBody SystemMenuInfoVo vo) {
        menuSettingService.updateInfo(vo);
    }

    @ApiOperation("菜单信息")
    @GetMapping("/info/{id}")
    public SystemMenu detailInfo(@Validated(IdParam.class) @PathVariable("id") Long id) {
        return menuSettingService.detailInfo(id);
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除菜单", event = LogOperation.EVENT_DELETE)
    public void deleteInfo(@Validated(IdParam.class) @PathVariable("id") Long id) {
        menuSettingService.deleteInfo(id);
    }

    @ApiOperation("置顶菜单")
    @PutMapping("/top")
    @LoggerInfo(value = "置顶菜单", event = LogOperation.EVENT_UPDATE)
    public void topInfo(@Validated(IdParam.class) @RequestBody SystemMenuInfoVo param) {
        menuSettingService.topInfo(param.getId());
    }

    @ApiOperation("初始化菜单code")
    public List<SystemMenu> initMenuCode() {
        return menuSettingService.initMenuCode();
    }
}
