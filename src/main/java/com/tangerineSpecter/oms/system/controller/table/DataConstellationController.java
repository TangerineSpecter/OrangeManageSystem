package com.tangerinespecter.oms.system.controller.table;

import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.table.IDataConstellationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 星座数据相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月8日
 */
@RestController
@RequestMapping("/table/constellation")
public class DataConstellationController {

    @Resource
    private IDataConstellationService dataConstellationService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 星座页面
     */
    @RequiresPermissions("table:constellation:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getConstellationPageKey, "data/constellation");
    }

    /**
     * 星座列表
     */
    @AccessLimit(maxCount = 10)
    @RequestMapping("/list")
    public ServiceResult listInfo(ConstellationQueryObject qo) {
        return dataConstellationService.queryForPage(qo);
    }
}
