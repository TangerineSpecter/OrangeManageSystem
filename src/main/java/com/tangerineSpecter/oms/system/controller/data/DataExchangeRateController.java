package com.tangerinespecter.oms.system.controller.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.FoodLibraryQueryObject;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.job.service.ExchangeRateQuartzService;
import com.tangerinespecter.oms.system.domain.entity.DataFoodLibrary;
import com.tangerinespecter.oms.system.domain.vo.data.FoodLibraryInfoVo;
import com.tangerinespecter.oms.system.service.data.IDataFoodLibraryService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * 货币汇率相关
 *
 * @author 丢失的橘子
 * @version 0.5.1
 * @Date 2023年01月12日00:31:09
 */
@ReWriteBody
@RestController
@Api(tags = "货币汇率数据接口")
@RequiredArgsConstructor
@RequestMapping("/data/exchange-rate")
public class DataExchangeRateController {

    private final ExchangeRateQuartzService exchangeRateQuartzService;

    @ApiOperation("刷新当日汇率数据")
    @AccessLimit(maxCount = 10)
    @PostMapping("/refresh")
    public void refresh() {
        exchangeRateQuartzService.runData();
    }

}
