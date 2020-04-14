package com.tangerinespecter.oms.system.controller.data;

import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.data.IDateTradeRecordServer;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 交易数据相关
 *
 * @author TangerineSpecter
 * @Date 2020年04月14日10:16:23
 */
@RestController
@RequestMapping("/data/trade-record")
public class DataTradeRecordController {

    @Resource
    private IDateTradeRecordServer dateTradeRecordServer;
    @Resource
    private PageResultService pageResultService;

    /**
     * 交易记录页面
     */
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getTradeRecordPageKey, "data/tradeRecord");
    }

    /**
     * 交易记录列表
     */
    @AccessLimit(maxCount = 10)
    @RequestMapping("/list")
    public ServiceResult listInfo(TradeRecordQueryObject qo) {
        return dateTradeRecordServer.queryForPage(qo);
    }

    /**
     * 交易数据初始化
     */
    @AccessLimit(maxCount = 10)
    @RequestMapping("/init")
    public ServiceResult init() {
        return dateTradeRecordServer.init();
    }
}
