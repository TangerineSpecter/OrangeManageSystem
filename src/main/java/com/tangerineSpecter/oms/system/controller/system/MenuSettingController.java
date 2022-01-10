package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IMenuSettingService;
import com.tangerinespecter.oms.system.valid.IdParam;
import com.tangerinespecter.oms.system.valid.Insert;
import com.tangerinespecter.oms.system.valid.Update;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 菜单设置控制
 *
 * @author TangerineSpecter
 * @date 2019年09月01日18:40:56
 */
@RestController
@RequestMapping("/system/menu")
public class MenuSettingController {

    @Resource
    private IMenuSettingService menuSettingService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 菜单管理
     */
    @RequiresPermissions("system:menu:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemMenuPageKey, "system/menuSetting");
    }

    /**
     * 添加页面
     */
    @GetMapping("/addPage")
    public ModelAndView addMenuPage(Model model) {
        model.addAttribute("menuList", menuSettingService.listInfo());
        return ServiceResult.jumpPage("system/addEditMenuSetting");
    }

    /**
     * 菜单列表
     */
    @GetMapping("/list")
    public ServiceResult<Object> listInfo() {
        return menuSettingService.listInfo();
    }

    /**
     * 添加菜单
     */
    @PostMapping("/insert")
    @LoggerInfo(value = "添加菜单", event = LogOperation.EVENT_ADD)
    public ServiceResult insertInfo(@Validated(Insert.class) @RequestBody SystemMenuInfoVo vo) {
        return menuSettingService.insertInfo(vo);
    }

    /**
     * 编辑菜单
     */
    @PutMapping("/update")
    @LoggerInfo(value = "编辑菜单", event = LogOperation.EVENT_UPDATE)
    public ServiceResult updateInfo(@Validated(Update.class) @RequestBody SystemMenuInfoVo vo) {
        return menuSettingService.updateInfo(vo);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{id}")
    public ServiceResult detailInfo(@PathVariable("id") Long id) {
        return menuSettingService.detailInfo(id);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除菜单", event = LogOperation.EVENT_DELETE)
    public ServiceResult deleteInfo(@PathVariable("id") Long id) {
        return menuSettingService.deleteInfo(id);
    }

    /**
     * 置顶菜单
     */
    @PutMapping("/top")
    @LoggerInfo(value = "置顶菜单", event = LogOperation.EVENT_UPDATE)
    public ServiceResult topInfo(@Validated(IdParam.class) @RequestBody SystemMenuInfoVo param) {
        return menuSettingService.topInfo(param.getId());
    }

    /**
     * 初始化菜单code
     *
     * @return
     */
    public List<SystemMenu> initMenuCode() {
        return menuSettingService.initMenuCode();
    }
}
