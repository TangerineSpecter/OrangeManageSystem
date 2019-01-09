package com.tangerineSpecter.oms.system.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangerineSpecter.oms.common.query.SystemUserQueryObject;
import com.tangerineSpecter.oms.common.result.ServiceResult;
import com.tangerineSpecter.oms.common.utils.ServiceKey;
import com.tangerineSpecter.oms.system.domain.pojo.IndexDataBean;
import com.tangerineSpecter.oms.system.service.system.SystemInfoService;
import com.tangerineSpecter.oms.system.service.system.SystemUserService;

/**
 * 系统相关控制
 * 
 * @author TangerineSpecter
 * @DateTime 2019年1月5日 02:07:12
 * @version v0.0.3
 *
 */
@Controller
public class SystemController {

	@Autowired
	private SystemInfoService systemInfoService;
	@Autowired
	private SystemUserService systemUseroService;

	/**
	 * 后台管理员
	 */
	@RequestMapping(ServiceKey.System.SYSTEM_USER_PAGE_LIST)
	public String systemUserPage(Model model, SystemUserQueryObject qo) {
		systemUseroService.querySystemUserList(model, qo);
		return "system/systemUser";
	}

	/**
	 * 日历
	 */
	@RequestMapping("/calendar")
	public String calendar() {
		return "system/calendar";
	}

	/**
	 * 系统信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(ServiceKey.System.SYSTEM_INFO)
	public ServiceResult getSystemInfo() {
		IndexDataBean indexData = new IndexDataBean();
		indexData.setSystemInfo(systemInfoService.getSystemInfo());
		return ServiceResult.success(indexData);
	}

}
