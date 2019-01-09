package com.tangerineSpecter.oms.system.service.table;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tangerineSpecter.oms.common.query.ConstellactionQueryObject;
import com.tangerineSpecter.oms.system.domain.DataConstellation;
import com.tangerineSpecter.oms.system.mapper.DataConstellationMapper;

@Service
public class DataConstellationService {

	@Autowired
	private DataConstellationMapper dataConstellationMapper;

	public List<DataConstellation> queryListAll() {
		return dataConstellationMapper.selectAll();
	}

	/**
	 * 分页查询
	 */
	public void queryForPage(Model model, ConstellactionQueryObject qo) {
		Long total = dataConstellationMapper.queryForPageCount(qo);
		if (total == 0) {
			model.addAttribute("list", Collections.emptyList());
			model.addAttribute("page", qo.getPage());
			model.addAttribute("total", 0);
			return;
		}
		model.addAttribute("list", dataConstellationMapper.queryForPage(qo));
		model.addAttribute("page", qo.getPage());
		model.addAttribute("total", total.intValue());
	}
}
