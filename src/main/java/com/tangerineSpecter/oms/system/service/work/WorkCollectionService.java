package com.tangerineSpecter.oms.system.service.work;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerineSpecter.oms.common.query.WorkCollectionQueryObject;
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
}
