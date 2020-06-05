package com.tangerinespecter.oms.system.controller.data;

import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.data.IDataTradeLogicService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 交易逻辑控制
 *
 * @author TangerineSpecter
 * @version 0.4.1
 * @date 2020年06月05日11:35:40
 */
@Controller
@RequestMapping("/data/trade-logic")
public class DataTradeLogicController {

    @Resource
    private IDataTradeLogicService dataTradeLogicService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 交易逻辑页面
     */
    @ResponseBody
    @RequiresPermissions("data:trade-logic:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getTradeLogicPageKey, "data/tradeLogic");
    }

    /**
     * 交易逻辑列表
     */
    @ResponseBody
    @AccessLimit(maxCount = 10)
    @RequestMapping("/list")
    public ServiceResult listInfo(TradeLogicQueryObject qo) {
        return dataTradeLogicService.queryForPage(qo);
    }
}
