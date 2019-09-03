package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.SystemMenuInfoVo;
import com.tangerinespecter.oms.system.service.system.MenuSettingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    private MenuSettingService menuSettingService;

    /**
     * 菜单管理
     */
    @RequestMapping("/menu-setting")
    public String menuPage() {
        return "layout/menuSetting";
    }

    /**
     * 添加页面
     */
    @RequestMapping("/addPage")
    public String addMenuPage(Model model) {
        model.addAttribute("menuList", menuSettingService.listInfo());
        return "system/addMenuSetting";
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
    public ServiceResult insertInfo(@Valid() SystemMenuInfoVo vo) {
        return menuSettingService.insertInfo(vo);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ServiceResult deleteInfo(@RequestParam("id") Long id) {
        return menuSettingService.deleteInfo(id);
    }
}
