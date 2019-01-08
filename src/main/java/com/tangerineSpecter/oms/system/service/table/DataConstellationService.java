package com.tangerineSpecter.oms.system.service.table;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangerineSpecter.oms.system.domain.DataConstellation;
import com.tangerineSpecter.oms.system.mapper.DataConstellationMapper;

@Service
public class DataConstellationService {

	@Autowired
	private DataConstellationMapper dataConstellationMapper;
	
	public List<DataConstellation> queryListAll() {
		return dataConstellationMapper.selectAll();
	}
}
