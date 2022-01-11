package com.tangerinespecter.oms.system.service.table.impl;

import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.mapper.DataConstellationMapper;
import com.tangerinespecter.oms.system.service.table.IDataConstellationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataConstellationServiceImpl implements IDataConstellationService {
	
	private final DataConstellationMapper dataConstellationMapper;
	
	@Override
	public List<DataConstellation> list(ConstellationQueryObject qo) {
		return dataConstellationMapper.queryForPage(qo);
	}
}
