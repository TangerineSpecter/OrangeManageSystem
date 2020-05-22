package com.tangerinespecter.oms.system.controller.tools;

import com.tangerinespecter.oms.system.domain.pojo.BingImageResponse;
import com.tangerinespecter.oms.system.domain.vo.tools.BingImageReq;
import com.tangerinespecter.oms.system.service.tools.IEveryDayWallPaperToolService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 每日壁纸工具
 *
 * @author TangerineSpecter
 * @version 0.3.4
 * @date 2020年05月22日14:22:32
 */
@Controller
@RequestMapping("/tools/wall-paper")
public class EveryDayWallPaperToolController {

    @Resource
    private IEveryDayWallPaperToolService everyDayWallPaperToolService;

    @RequestMapping("/page")
    @RequiresPermissions("tools:wall-paper:page")
    public String pageInfo(Model model) {
        BingImageResponse response = everyDayWallPaperToolService.wallPagerInfo(new BingImageReq());
        model.addAttribute("wallPaperList", response.getImages());
        return "tools/everyWallPaper";
    }

    @RequestMapping("/info")
    public BingImageResponse wallPagerInfo(BingImageReq req) {
        return everyDayWallPaperToolService.wallPagerInfo(req);
    }

}
