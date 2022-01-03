package com.tangerinespecter.oms.system.controller.data;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.data.AddTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.EditTradeLogicVo;
import com.tangerinespecter.oms.system.service.data.IDataTradeLogicService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 交易逻辑控制
 *
 * @author TangerineSpecter
 * @version 0.4.1
 * @date 2020年06月05日11:35:40
 */
@RestController
@RequestMapping("/data/trade-logic")
public class DataTradeLogicController {

    @Resource
    private IDataTradeLogicService dataTradeLogicService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 交易逻辑页面
     */
    @RequiresPermissions("data:trade-logic:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getTradeLogicPageKey, "data/tradeLogic");
    }

    /**
     * 添加页面
     */
    @GetMapping("/addPage")
    public ModelAndView addTradeRecordPage(Model model) {
        return ServiceResult.jumpPage("data/addTradeLogic");
    }

    /**
     * 编辑页面
     */
    @RequestMapping("/editPage")
    public ModelAndView editTradeRecordPage(Model model) {
        return ServiceResult.jumpPage("data/editTradeLogic");
    }

    /**
     * 交易逻辑列表
     */
    @AccessLimit(maxCount = 10)
    @GetMapping("/list")
    public ServiceResult listInfo(TradeLogicQueryObject qo) {
        return dataTradeLogicService.queryForPage(qo);
    }

    /**
     * 添加交易数据
     */
    @PostMapping("/insert")
    @LoggerInfo(value = "添加交易逻辑", event = LogOperation.EVENT_ADD)
    public ServiceResult insertInfo(@Validated @RequestBody AddTradeLogicVo vo) {
        return dataTradeLogicService.insertInfo(vo);
    }

    /**
     * 编辑交易数据
     */
    @PutMapping("/update")
    @LoggerInfo(value = "编辑交易逻辑", event = LogOperation.EVENT_UPDATE)
    public ServiceResult updateInfo(@Validated @RequestBody EditTradeLogicVo vo) {
        return dataTradeLogicService.updateInfo(vo);
    }

    /**
     * 删除交易逻辑
     */
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除交易逻辑", event = LogOperation.EVENT_DELETE)
    public ServiceResult deleteInfo(@PathVariable("id") Long id) {
        return dataTradeLogicService.deleteInfo(id);
    }
}
