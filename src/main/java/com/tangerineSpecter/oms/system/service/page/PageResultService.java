package com.tangerineSpecter.oms.system.service.page;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * 分页处理
 * 
 * @author TangerineSpecter
 * @Date 2019年1月9日
 * @version v0.0.5
 */
@Service
public class PageResultService {

	/**
	 * 分页处理
	 * 
	 * @param model
	 * @param list
	 *            列表
	 * @param total
	 *            总数
	 * @param page
	 *            页数
	 * @param requestUrl
	 *            请求地址
	 */
	public void queryForPage(Model model, List<?> list, Long total, Integer page, String requestUrl) {
		model.addAttribute("page", page);
		model.addAttribute("url", requestUrl);
		if (total == 0) {
			model.addAttribute("list", Collections.emptyList());
			model.addAttribute("total", 0);
			return;
		}
		model.addAttribute("list", list);
		model.addAttribute("total", total.intValue());
	}
}
