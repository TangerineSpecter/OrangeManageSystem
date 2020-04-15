package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IMenuSetting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 菜单设置控制
 *
 * @author TangerineSpecter
 * @date 2019年09月01日18:40:56
 */
@Controller
@RequestMapping("/system/menu")
public class MenuSettingController {

    @Resource
    private IMenuSetting menuSettingService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 菜单管理
     */
    @ResponseBody
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemMenuPageKey, "layout/menuSetting");
    }

    /**
     * 添加页面
     */
    @RequestMapping("/addPage")
    public String addMenuPage(Model model) {
        model.addAttribute("menuList", menuSettingService.listInfo());
        return "system/addEditMenuSetting";
    }

    /**
     * 菜单列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult<Object> listInfo() {
        return menuSettingService.listInfo();
    }

    /**
     * 添加菜单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/insert")
    @LoggerInfo(value = "添加菜单", event = LogOperation.EVENT_ADD)
    public ServiceResult insertInfo(@Valid() SystemMenuInfoVo vo) {
        return menuSettingService.insertInfo(vo);
    }

    /**
     * 编辑菜单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    @LoggerInfo(value = "编辑菜单", event = LogOperation.EVENT_UPDATE)
    public ServiceResult updateInfo(@Valid() SystemMenuInfoVo vo) {
        return menuSettingService.updateInfo(vo);
    }

    /**
     * 菜单信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/info")
    public ServiceResult detailInfo(@RequestParam("id") Long id) {
        return menuSettingService.detailInfo(id);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    @LoggerInfo(value = "删除菜单", event = LogOperation.EVENT_DELETE)
    public ServiceResult deleteInfo(@RequestParam("id") Long id) {
        return menuSettingService.deleteInfo(id);
    }
}
