package com.tangerineSpecter.oms.job.mapper;

import com.tangerineSpecter.oms.job.domain.DataConstellation;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataConstellationMapper {
	int deleteByPrimaryKey(Long id);

	int insert(DataConstellation record);

	DataConstellation selectByPrimaryKey(Long id);

	List<DataConstellation> selectAll();

	int updateByPrimaryKey(DataConstellation record);

	/**
	 * 根据创建时间查询星座名字
	 */
	List<String> queryListByCreateTime(@Param("createTime") String create_time);
}