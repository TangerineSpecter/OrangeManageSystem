package com.tangerinespecter.oms.system.controller.table;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.query.FundHistoryQueryObject;
import com.tangerinespecter.oms.common.query.FundQueryObject;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.table.IDataFundHistoryService;
import com.tangerinespecter.oms.system.service.table.IDataFundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 基金数据相关控制
 *
 * @author TangerineSpecter
 * @version v0.5.1
 * @date 2022年10月16日
 */
@RestController
@ReWriteBody
@Api(tags = "基金数据接口")
@RequiredArgsConstructor
@RequestMapping("/table/fund")
public class FundDataController {

    private final IDataFundService dataFundService;
    private final IDataFundHistoryService dataFundHistoryService;
    private final PageResultService pageResultService;

    /**
     * 基金数据页面
     */
    @ApiOperation(value = "基金数据页面")
    @RequiresPermissions("table:fund:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getFundPageKey, "data/fund");
    }

    /**
     * 基金列表
     */
    @AccessLimit(maxCount = 50)
    @ApiOperation("基金列表")
    @PostMapping("/list")
    public PageInfo<DataFund> listInfo(@RequestBody QueryObject<FundQueryObject> param) {
        return dataFundService.queryForPage(param);
    }

    /**
     * 基金历史数据列表
     */
    @AccessLimit(maxCount = 50)
    @ApiOperation("基金历史数据列表")
    @PostMapping("/history-list")
    public PageInfo<DataFundHistory> historyListInfo(@RequestBody QueryObject<FundHistoryQueryObject> param) {
        return dataFundHistoryService.queryForPage(param);
    }

    @ApiOperation("基金数据初始化")
    @PostMapping("/init-fund")
    public int initFund() {
        return CollUtil.size(dataFundService.initFund().getAllFundData());
    }

    @ApiOperation("基金历史数据初始化")
    @PostMapping("/init-fund-history")
    public void initFundHistory(@ApiParam("基金代码") @RequestBody List<String> fundCode) {
        dataFundService.initFundHistory(fundCode);
    }
}
