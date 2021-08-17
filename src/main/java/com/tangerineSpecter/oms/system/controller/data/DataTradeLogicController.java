package com.tangerinespecter.oms.system.controller.data;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 交易逻辑控制
 *
 * @author TangerineSpecter
 * @version 0.4.1
 * @date 2020年06月05日11:35:40
 */
@Controller
@RequestMapping("/data/trade-logic")
public class DataTradeLogicController {

    @Resource
    private IDataTradeLogicService dataTradeLogicService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 交易逻辑页面
     */
    @ResponseBody
    @SaCheckPermission("data:trade-logic:page")
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getTradeLogicPageKey, "data/tradeLogic");
    }

    /**
     * 添加页面
     */
    @RequestMapping("/addPage")
    public String addTradeRecordPage(Model model) {
        return "data/addTradeLogic";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("/editPage")
    public String editTradeRecordPage(Model model) {
        return "data/editTradeLogic";
    }

    /**
     * 交易逻辑列表
     */
    @ResponseBody
    @AccessLimit(maxCount = 10)
    @RequestMapping("/list")
    public ServiceResult listInfo(TradeLogicQueryObject qo) {
        return dataTradeLogicService.queryForPage(qo);
    }

    /**
     * 添加交易数据
     */
    @ResponseBody
    @RequestMapping("/insert")
    @LoggerInfo(value = "添加交易逻辑", event = LogOperation.EVENT_ADD)
    public ServiceResult insertInfo(@Valid() AddTradeLogicVo vo) {
        return dataTradeLogicService.insertInfo(vo);
    }

    /**
     * 编辑交易数据
     */
    @ResponseBody
    @RequestMapping("/update")
    @LoggerInfo(value = "编辑交易逻辑", event = LogOperation.EVENT_UPDATE)
    public ServiceResult updateInfo(@Valid() EditTradeLogicVo vo) {
        return dataTradeLogicService.updateInfo(vo);
    }

    /**
     * 删除交易逻辑
     */
    @ResponseBody
    @RequestMapping("/delete")
    @LoggerInfo(value = "删除交易逻辑", event = LogOperation.EVENT_DELETE)
    public ServiceResult deleteInfo(@RequestParam("id") Long id) {
        return dataTradeLogicService.deleteInfo(id);
    }
}
