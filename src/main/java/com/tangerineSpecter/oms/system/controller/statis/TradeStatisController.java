package com.tangerinespecter.oms.system.controller.statis;

import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 交易统计
 *
 * @author 丢失的橘子
 * @version 0.2.1
 * @date 2020年04月28日11:41:28
 */
@ReWriteBody
@RestController
@Api(tags = "交易统计接口")
@RequestMapping("/statis/trade")
@RequiredArgsConstructor
public class TradeStatisController {

    private final PageResultService pageResultService;
    private final ITradeStatisService tradeStatisService;
    private final ISystemInfoService systemInfoService;

    @ApiOperation("交易统计页面")
    @RequiresPermissions("statis:trade:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("statisInfo", systemInfoService.getStatisticsInfo());
        model.addAttribute("incomeInfo", tradeStatisService.incomeValueStatisInfo(1));
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getTradeStatisPageKey, "statis/tradeStatis");
    }

    @ApiOperation("收益数据统计")
    @GetMapping(value = "income-data/{type}")
    public TradeStatisIncomeInfoDto incomeInfo(@ApiParam("类型，1：每日；2：每月，默认1") @PathVariable(name = "type") Integer type) {
        return tradeStatisService.incomeValueStatisInfo(type);
    }
}
