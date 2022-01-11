package com.tangerinespecter.oms.system.controller.data;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import com.tangerinespecter.oms.system.service.data.IDateTradeRecordService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 交易数据相关
 *
 * @author TangerineSpecter
 * @Date 2020年04月14日10:16:23
 */
@RestController
@Api(tags = "交易记录接口")
@RequestMapping("/data/trade-record")
public class DataTradeRecordController {
	
	@Resource
	private IDateTradeRecordService dateTradeRecordService;
	@Resource
	private PageResultService pageResultService;
	
	/**
	 * 交易记录页面
	 */
	@RequiresPermissions("data:trade-record:page")
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getTradeRecordPageKey, "data/tradeRecord");
	}
	
	/**
	 * 交易记录列表
	 */
	@AccessLimit(maxCount = 10)
	@GetMapping("/list")
	public ServiceResult listInfo(TradeRecordQueryObject qo) {
		return dateTradeRecordService.queryForPage(qo);
	}
	
	/**
	 * 添加页面
	 */
	@GetMapping("/addPage")
	public ModelAndView addTradeRecordPage() {
		return ServiceResult.jumpPage("data/addEditTradeRecord");
	}
	
	/**
	 * 交易数据初始化
	 */
	@AccessLimit(maxCount = 10)
	@PostMapping("/init")
	public ServiceResult init() {
		return dateTradeRecordService.init();
	}
	
	/**
	 * 添加交易数据
	 */
	@PostMapping("/insert")
	@LoggerInfo(value = "添加交易数据", event = LogOperation.EVENT_ADD)
	public ServiceResult insertInfo(@Validated @RequestBody TradeRecordInfoVo vo) {
		return dateTradeRecordService.insertInfo(vo);
	}
	
	/**
	 * 编辑交易数据
	 */
	@PutMapping("/update")
	@LoggerInfo(value = "编辑交易数据", event = LogOperation.EVENT_UPDATE)
	public ServiceResult updateInfo(@Validated @RequestBody TradeRecordInfoVo vo) {
		return dateTradeRecordService.updateInfo(vo);
	}
	
	/**
	 * 交易数据详情
	 */
	@GetMapping("/info/{id}")
	public ServiceResult detailInfo(@PathVariable("id") Long id) {
		return dateTradeRecordService.detailInfo(id);
	}
	
	/**
	 * 删除交易数据
	 */
	@DeleteMapping("/delete/{id}")
	@LoggerInfo(value = "删除交易数据", event = LogOperation.EVENT_DELETE)
	public ServiceResult deleteInfo(@PathVariable("id") Long id) {
		return dateTradeRecordService.deleteInfo(id);
	}
	
	/**
	 * excel导入数据
	 */
	@PostMapping("/excel")
	public ServiceResult excelInfo(MultipartFile file) {
		return dateTradeRecordService.excelInfo(file);
	}
}
