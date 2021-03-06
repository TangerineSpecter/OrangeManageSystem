package com.tangerinespecter.oms.system.controller.statis;

import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.statis.IHealthStatisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 健康管理
 *
 * @author liangjun.zhou
 * @version 0.3.1
 * @date 2020年05月09日23:02:39
 */
@RestController
@RequestMapping("/statis/health")
public class HealthStatisController {

    @Resource
    private PageResultService pageResultService;
    @Resource
    private IHealthStatisService healthStatisService;

    /**
     * 健康统计页面
     */
    @RequiresPermissions("statis:health:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getHealthStatisPageKey, "statis/healthStatis");
    }

    @RequestMapping("/health-info")
    public ServiceResult healthStatisInfo() {
        return healthStatisService.healthStatisInfo();
    }
}
