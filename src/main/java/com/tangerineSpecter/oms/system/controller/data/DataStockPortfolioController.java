package com.tangerinespecter.oms.system.controller.data;

import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.query.StockPortfolioQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.data.IDataStockPortfolioService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 交易组合
 * @author 丢失的橘子
 */
@RestController
@RequestMapping("/data/stock-portfolio")
public class DataStockPortfolioController {

    @Resource
    private PageResultService pageResultService;
    @Resource
    private IDataStockPortfolioService dataStockPortfolioService;

    /**
     * 交易记录页面
     */
    @RequiresPermissions("data:stock-portfolio:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getStockPortfolioPageKey, "data/tradeRecord");
    }

    /**
     * 交易记录列表
     */
    @AccessLimit(maxCount = 10)
    @RequestMapping("/list")
    public ServiceResult listInfo(StockPortfolioQueryObject qo) {
        return dataStockPortfolioService.queryForPage(qo);
    }
}
