package com.tangerinespecter.oms.system.controller.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import com.tangerinespecter.oms.system.domain.vo.data.AddTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.EditTradeLogicVo;
import com.tangerinespecter.oms.system.service.data.IDataTradeLogicService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
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
 * 交易逻辑控制
 *
 * @author TangerineSpecter
 * @version 0.4.1
 * @date 2020年06月05日11:35:40
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "交易逻辑接口")
@RequestMapping("/data/trade-logic")
public class DataTradeLogicController {

    private final IDataTradeLogicService dataTradeLogicService;
    private final PageResultService pageResultService;

    @ApiOperation("交易逻辑页面")
    @RequiresPermissions("data:trade-logic:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.TRADE_LOGIC_PAGE_KEY, "data/tradeLogic");
    }

    @ApiOperation("添加页面")
    @GetMapping("/addPage")
    public ModelAndView addTradeRecordPage(Model model) {
        return ServiceResult.jumpPage("data/addTradeLogic");
    }

    /**
     * 编辑页面
     */
    @ApiOperation("编辑页面")
    @GetMapping("/editPage")
    public ModelAndView editTradeRecordPage(Model model) {
        return ServiceResult.jumpPage("data/editTradeLogic");
    }

    @ApiOperation("交易逻辑列表")
    @AccessLimit(maxCount = 10)
    @PostMapping("/list")
    public PageInfo<DataTradeLogic> listInfo(@RequestBody QueryObject<TradeLogicQueryObject> qo) {
        return dataTradeLogicService.queryForPage(qo);
    }

    @ApiOperation("添加交易逻辑")
    @PostMapping("/insert")
    @LoggerInfo(value = "添加交易逻辑", event = LogOperation.EVENT_ADD)
    public void insertInfo(@Validated @RequestBody AddTradeLogicVo vo) {
        dataTradeLogicService.insertInfo(vo);
    }

    @ApiOperation("编辑交易逻辑")
    @PutMapping("/update")
    @LoggerInfo(value = "编辑交易逻辑", event = LogOperation.EVENT_UPDATE)
    public void updateInfo(@Validated @RequestBody EditTradeLogicVo vo) {
        dataTradeLogicService.updateInfo(vo);
    }


    @ApiOperation("删除交易逻辑")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除交易逻辑", event = LogOperation.EVENT_DELETE)
    public void deleteInfo(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
        dataTradeLogicService.deleteInfo(id);
    }
}
