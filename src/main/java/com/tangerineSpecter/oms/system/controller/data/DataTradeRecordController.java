package com.tangerinespecter.oms.system.controller.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import com.tangerinespecter.oms.system.service.data.IDataTradeRecordService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * 交易数据相关
 *
 * @author TangerineSpecter
 * @Date 2020年04月14日10:16:23
 */
@ReWriteBody
@RestController
@Api(tags = "交易记录接口")
@RequiredArgsConstructor
@RequestMapping("/data/trade-record")
public class DataTradeRecordController {

    private final IDataTradeRecordService dataTradeRecordService;
    private final PageResultService pageResultService;

    @ApiOperation("交易记录页面")
    @RequiresPermissions("data:trade-record:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.TRADE_RECORD_PAGE_KEY, "data/tradeRecord");
    }

    @ApiOperation("交易记录列表")
    @AccessLimit(maxCount = 10)
    @PostMapping("/list")
    public PageInfo<DataTradeRecord> listInfo(@RequestBody QueryObject<TradeRecordQueryObject> qo) {
        return dataTradeRecordService.queryForPage(qo);
    }

    @ApiOperation("添加编辑页面")
    @GetMapping("/addPage")
    public ModelAndView addTradeRecordPage() {
        return ServiceResult.jumpPage("data/addEditTradeRecord");
    }

    @ApiOperation("交易记录初始化")
    @AccessLimit(maxCount = 10)
    @PostMapping("/init")
    public void init() {
        dataTradeRecordService.init();
    }

    @ApiOperation("添加交易记录")
    @PostMapping("/insert")
    @LoggerInfo(value = "添加交易数据", event = LogOperation.EVENT_ADD)
    public void insertInfo(@Validated @RequestBody TradeRecordInfoVo vo) {
        dataTradeRecordService.insertInfo(vo);
    }

    @ApiOperation("编辑交易记录")
    @PutMapping("/update")
    @LoggerInfo(value = "编辑交易数据", event = LogOperation.EVENT_UPDATE)
    public void updateInfo(@Validated(Update.class) @RequestBody TradeRecordInfoVo vo) {
        dataTradeRecordService.updateInfo(vo);
    }

    @ApiOperation("交易记录信息")
    @GetMapping("/info/{id}")
    public DataTradeRecord detailInfo(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
        return dataTradeRecordService.detailInfo(id);
    }

    @ApiOperation("删除交易记录")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除交易数据", event = LogOperation.EVENT_DELETE)
    public void deleteInfo(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
        dataTradeRecordService.deleteInfo(id);
    }

    @ApiOperation("导入交易数据")
    @PostMapping("/excel")
    public void excelInfo(MultipartFile file) {
        dataTradeRecordService.excelInfo(file);
    }

}
