package com.tangerineSpecter.oms.system.web.controller.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tangerineSpecter.oms.system.service.table.DataConstellationService;

/**
 * 星座数据相关控制
 * 
 * @author TangerineSpecter
 * @Date 2019年1月8日
 * @version v0.0.5
 */
@Controller
public class DataConstellationController {

	@Autowired
	private DataConstellationService dataConstellationService;

	/**
	 * 星座页面
	 */
	@RequestMapping("/constellaction")
	public String constellactionPage(Model model) {
		model.addAttribute(dataConstellationService.queryListAll());
		return "table/constellaction";
	}
}
