package com.tangerinespecter.oms.system.controller.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.domain.entity.DataStock;
import com.tangerinespecter.oms.system.service.data.IDataStockService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 股票池控制层
 *
 * @author tangerineSpecter
 * @version 0.0.7
 * @date 2020年04月15日01:39:16
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "股票池接口")
@RequestMapping("/data/stock")
public class DataStockController {

    private final PageResultService pageResultService;
    private final IDataStockService dataStockService;

    /**
     * 股票池页面
     */
    @ApiOperation("股票池页面")
    @RequiresPermissions("data:stock:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getStockPageKey, "data/stock");
    }

    @ApiOperation("股票池列表")
    @AccessLimit(maxCount = 10)
    @PostMapping("/list")
    public PageInfo<DataStock> listInfo(@RequestBody QueryObject<StockQueryObject> qo) {
        return dataStockService.queryForPage(qo);
    }
}
