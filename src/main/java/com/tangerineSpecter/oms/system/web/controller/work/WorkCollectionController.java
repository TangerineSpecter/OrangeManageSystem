package com.tangerineSpecter.oms.system.web.controller.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangerineSpecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerineSpecter.oms.common.result.ServiceResult;
import com.tangerineSpecter.oms.common.utils.ServiceKey;
import com.tangerineSpecter.oms.system.domain.WorkCollection;
import com.tangerineSpecter.oms.system.service.work.WorkCollectionService;

/**
 * 收藏信息相关控制
 * 
 * @author TangerineSpecter
 * @Date 2019年1月22日
 * @version v0.1.2
 */
@Controller
public class WorkCollectionController {

	@Autowired
	private WorkCollectionService workCollectionService;

	/**
	 * 收藏页面
	 */
	@RequestMapping(ServiceKey.Work.COLLECTION_PAGE_LIST)
	public String constellactionPage(Model model, WorkCollectionQueryObject qo) {
		workCollectionService.queryForPage(model, qo);
		return "work/collection";
	}

	/**
	 * 新增收藏
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(ServiceKey.Work.COLLECTION_ADD)
	public ServiceResult insert(WorkCollection data) {
		return workCollectionService.insert(data);
	}
	
	/**
	 * 删除收藏
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(ServiceKey.Work.COLLECTION_DELETE)
	public ServiceResult delete(Long id) {
		return workCollectionService.delete(id);
	}
}
