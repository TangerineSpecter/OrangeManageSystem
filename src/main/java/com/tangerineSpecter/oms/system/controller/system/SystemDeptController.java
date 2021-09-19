package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.system.SystemDeptVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.ISystemDeptService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 部门管理相关控制
 *
 * @author TangerineSpecter
 * @date 2020年02月15日21:25:06
 */
@RestController("/systemDept")
public class SystemDeptController {

	@Resource
	private ISystemDeptService systemDeptService;
	@Resource
	private PageResultService pageResultService;

	/**
	 * 部门管理
	 */
	@RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemDeptPageKey, "system/systemDept");
	}

	/**
	 * 新增部门
	 */
	@PostMapping("/insert")
	public ServiceResult<?> insert(@Valid SystemDeptVo vo) {
		return systemDeptService.insertSystemDeptInfo(vo);
	}
}
