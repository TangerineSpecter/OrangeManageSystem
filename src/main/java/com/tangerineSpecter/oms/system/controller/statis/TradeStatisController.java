package com.tangerinespecter.oms.system.controller.statis;

import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisServer;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 交易统计
 *
 * @author liangjun.zhou
 * @version 0.2.1
 * @date 2020年04月28日11:41:28
 */
@RestController
@RequestMapping("/statis/trade")
public class TradeStatisController {

    @Resource
    private PageResultService pageResultService;
    @Resource
    private ITradeStatisServer tradeStatisServer;

    /**
     * 交易统计页面
     */
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getTradeStatisPageKey, "statis/tradeStatis");
    }

    @RequestMapping("/income-info")
    public ServiceResult incomeValueStatisInfo() {
        return tradeStatisServer.incomeValueStatisInfo();
    }
}
