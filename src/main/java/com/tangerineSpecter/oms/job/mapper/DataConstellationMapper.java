package com.tangerineSpecter.oms.job.mapper;

import com.tangerineSpecter.oms.job.domain.DataConstellation;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataConstellationMapper {
	int deleteByPrimaryKey(Long id);

	int insert(DataConstellation record);

	DataConstellation selectByPrimaryKey(Long id);

	List<DataConstellation> selectAll();

	int updateByPrimaryKey(DataConstellation record);
}