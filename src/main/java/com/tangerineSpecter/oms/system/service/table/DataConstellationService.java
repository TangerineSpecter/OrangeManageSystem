package com.tangerineSpecter.oms.system.service.table;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerineSpecter.oms.common.query.ConstellactionQueryObject;
import com.tangerineSpecter.oms.common.utils.ServiceKey;
import com.tangerineSpecter.oms.system.domain.DataConstellation;
import com.tangerineSpecter.oms.system.mapper.DataConstellationMapper;
import com.tangerineSpecter.oms.system.service.page.PageResultService;

@Service
public class DataConstellationService {

	@Autowired
	private DataConstellationMapper dataConstellationMapper;
	@Autowired
	private PageResultService pageResultService;

	public List<DataConstellation> queryListAll() {
		return dataConstellationMapper.selectAll();
	}

	/**
	 * 分页查询
	 */
	public void queryForPage(Model model, ConstellactionQueryObject qo) {
		PageHelper.startPage(qo.getPage(), qo.getSize());
		List<DataConstellation> pageList = dataConstellationMapper.queryForPage(qo);
		PageInfo<DataConstellation> constellationInfo = new PageInfo<>(pageList);
		pageResultService.queryForPage(model, constellationInfo.getList(), constellationInfo.getTotal(), qo.getPage(),
				constellationInfo.getPages(), ServiceKey.Constellation.CONSTELLATION_PAGE_LIST);
	}
}
