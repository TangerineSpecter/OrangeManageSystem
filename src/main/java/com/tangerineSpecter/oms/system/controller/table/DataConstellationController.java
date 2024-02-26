package com.tangerinespecter.oms.system.controller.table;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.table.IDataConstellationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 星座数据相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @date 2019年1月8日
 */
@RestController
@ReWriteBody
@Api(tags = "星座数据接口")
@RequiredArgsConstructor
@RequestMapping("/table/constellation")
public class DataConstellationController {

    private final IDataConstellationService dataConstellationService;

    private final PageResultService pageResultService;

    /**
     * 星座页面
     */
    @ApiOperation(value = "星座页面")
    @RequiresPermissions("table:constellation:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.CONSTELLATION_PAGE_KEY, "data/constellation");
    }

    /**
     * 星座列表
     */
    @AccessLimit(maxCount = 10)
    @ApiOperation("星座列表")
    @PostMapping("/list")
    public PageInfo<DataConstellation> listInfo(@RequestBody QueryObject<ConstellationQueryObject> param) {
        return dataConstellationService.queryForPage(param);
    }
}
