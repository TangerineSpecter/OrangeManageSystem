package com.tangerinespecter.oms.system.controller.data;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import com.tangerinespecter.oms.system.service.data.IDateTradeRecordServer;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 交易数据相关
 *
 * @author TangerineSpecter
 * @Date 2020年04月14日10:16:23
 */
@Controller
@RequestMapping("/data/trade-record")
public class DataTradeRecordController {

    @Resource
    private IDateTradeRecordServer dateTradeRecordServer;
    @Resource
    private PageResultService pageResultService;

    /**
     * 交易记录页面
     */
    @ResponseBody
    @RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getStockPortfolioPageKey, "data/tradeRecord");
    }

    /**
     * 交易记录列表
     */
    @ResponseBody
    @AccessLimit(maxCount = 10)
    @RequestMapping("/list")
    public ServiceResult listInfo(TradeRecordQueryObject qo) {
        return dateTradeRecordServer.queryForPage(qo);
    }

    /**
     * 添加页面
     */
    @RequestMapping("/addPage")
    public String addTradeRecordPage(Model model) {
        return "data/addEditTradeRecord";
    }

    /**
     * 交易数据初始化
     */
    @AccessLimit(maxCount = 10)
    @ResponseBody
    @RequestMapping("/init")
    public ServiceResult init() {
        return dateTradeRecordServer.init();
    }

    /**
     * 添加交易数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/insert")
    @LoggerInfo(value = "添加交易数据", event = LogOperation.EVENT_ADD)
    public ServiceResult insertInfo(@Valid() TradeRecordInfoVo vo) {
        return dateTradeRecordServer.insertInfo(vo);
    }

    /**
     * 编辑交易数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    @LoggerInfo(value = "编辑交易数据", event = LogOperation.EVENT_UPDATE)
    public ServiceResult updateInfo(@Valid() TradeRecordInfoVo vo) {
        return dateTradeRecordServer.updateInfo(vo);
    }

    /**
     * 交易数据详情
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/info")
    public ServiceResult detailInfo(@RequestParam("id") Long id) {
        return dateTradeRecordServer.detailInfo(id);
    }

    /**
     * 删除交易数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    @LoggerInfo(value = "删除交易数据", event = LogOperation.EVENT_DELETE)
    public ServiceResult deleteInfo(@RequestParam("id") Long id) {
        return dateTradeRecordServer.deleteInfo(id);
    }

    /**
     * excel导入数据
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/excel")
    public ServiceResult excelInfo(MultipartFile file) {
        return dateTradeRecordServer.excelInfo(file);
    }
}
