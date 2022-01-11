package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemBulletinInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.ISystemBulletinService;
import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * 系统公告管理
 *
 * @author TangerineSpecter
 * @version 0.3.0
 * @date 2020年05月08日22:07:42
 */
@RestController
@Api(tags = "系统公告接口")
@RequestMapping("/system/bulletin")
public class SystemBulletinController {
	
	@Resource
	private PageResultService pageResultService;
	@Resource
	private ISystemBulletinService systemBulletinService;
	
	/**
	 * 系统公告
	 */
	@ApiOperation("系统公告页面")
	@RequiresPermissions("system:bulletin:page")
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String bulletinPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemBulletinPageKey, "system/systemBulletin");
	}
	
	/**
	 * 添加页面
	 */
	@ApiOperation("添加编辑公告页面")
	@GetMapping("/addPage")
	public ModelAndView addBulletinPage(Model model) {
		return ServiceResult.jumpPage("system/addEditBulletin");
	}
	
	/**
	 * 公告列表
	 */
	@ApiOperation("系统公告列表")
	@GetMapping("/list")
	public ServiceResult bulletinPage(Model model, SystemBulletinQueryObject qo) {
		return systemBulletinService.queryForPage(model, qo);
	}
	
	/**
	 * 新增公告
	 */
	@ApiOperation("新增系统公告")
	@PostMapping("/insert")
	@LoggerInfo(value = "新增公告", event = LogOperation.EVENT_ADD)
	public ServiceResult insert(@Validated @RequestBody SystemBulletinInfoVo data) {
		return systemBulletinService.insert(data);
	}
	
	/**
	 * 编辑公告
	 */
	@ApiOperation("编辑系统公告")
	@PutMapping("/update")
	@LoggerInfo(value = "更新公告", event = LogOperation.EVENT_UPDATE)
	public ServiceResult update(@Validated(Update.class) @RequestBody SystemBulletinInfoVo param) {
		return systemBulletinService.update(param);
	}
	
	/**
	 * 删除公告
	 */
	@ApiOperation("删除系统公告")
	@DeleteMapping("/delete/{id}")
	@LoggerInfo(value = "删除公告", event = LogOperation.EVENT_DELETE)
	public ServiceResult delete(@NotNull(message = "id不能为空") @PathVariable("id") Long id) {
		return systemBulletinService.delete(id);
	}
	
	/**
	 * 置顶公告
	 */
	@ApiOperation("置顶系统公告")
	@PutMapping("/top")
	@LoggerInfo(value = "置顶公告", event = LogOperation.EVENT_UPDATE)
	public ServiceResult topInfo(@Validated(Update.class) @RequestBody SystemBulletinInfoVo param) {
		return systemBulletinService.topInfo(param.getId());
	}
}
