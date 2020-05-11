package com.tangerinespecter.oms.system.controller.tools;

import com.tangerinespecter.oms.system.service.tools.ITradeToolService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 交易小工具
 *
 * @author TangerineSpecter
 * @version 0.3.1
 * @date 2020年05月11日15:21:47
 */
@RestController
@RequestMapping("/tools/trade")
public class TradeToolController {

    @Resource
    private ITradeToolService tradeToolService;

    //TODO 待增加工具方法
}
