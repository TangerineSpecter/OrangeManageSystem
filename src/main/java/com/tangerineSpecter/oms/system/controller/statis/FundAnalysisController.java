package com.tangerinespecter.oms.system.controller.statis;

import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.domain.dto.statis.FundAnalysisInfoDto;
import com.tangerinespecter.oms.system.domain.vo.statis.FundAnalysisInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.statis.IFundAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基金数据分析
 *
 * @author 丢失的橘子
 * @version 0.5.1
 * @date 2022年10月27日16:41:37
 */
@ReWriteBody
@RestController
@Api(tags = "基金分析接口")
@RequestMapping("/analysis/fund")
@RequiredArgsConstructor
public class FundAnalysisController {

    private final PageResultService pageResultService;
    private final IFundAnalysisService fundAnalysisService;

    @ApiOperation("基金分析页面")
    @RequiresPermissions("analysis:fund:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getFundAnalysisPageKey, "statis/fundAnalysis");
    }

    @ApiOperation("基金分析")
    @PostMapping(value = "analysis")
    public FundAnalysisInfoDto analysis(@Validated @RequestBody FundAnalysisInfoVo vo) {
        return fundAnalysisService.analysis(vo);
    }
}
