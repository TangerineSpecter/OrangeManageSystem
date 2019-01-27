package com.tangerineSpecter.oms.system.service.work;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.yaml.snakeyaml.scanner.Constant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerineSpecter.oms.common.constant.CommonConstant;
import com.tangerineSpecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerineSpecter.oms.common.result.ServiceResult;
import com.tangerineSpecter.oms.common.utils.ServiceKey;
import com.tangerineSpecter.oms.system.domain.WorkCollection;
import com.tangerineSpecter.oms.system.mapper.WorkCollectionMapper;
import com.tangerineSpecter.oms.system.service.page.PageResultService;

@Service
public class WorkCollectionService {

	@Autowired
	private WorkCollectionMapper workCollectionMapper;
	@Autowired
	private PageResultService pageResultService;

	/**
	 * 分页查询
	 */
	public void queryForPage(Model model, WorkCollectionQueryObject qo) {
		PageHelper.startPage(qo.getPage(), qo.getSize());
		List<WorkCollection> pageList = workCollectionMapper.queryForPage(qo);
		PageInfo<WorkCollection> collectionInfo = new PageInfo<>(pageList);
		pageResultService.queryForPage(model, collectionInfo.getList(), collectionInfo.getTotal(), qo.getPage(),
				collectionInfo.getPages(), ServiceKey.Work.COLLECTION_PAGE_LIST);
	}

	/**
	 * 新增收藏
	 */
	@Transactional
	public ServiceResult insert(WorkCollection data) {
		data.setSort(CommonConstant.Number.COMMON_NUMBER_ZERO);
		data.setIsDel(CommonConstant.IS_DEL_NO);
		workCollectionMapper.insert(data);
		return ServiceResult.success();
	}
}
