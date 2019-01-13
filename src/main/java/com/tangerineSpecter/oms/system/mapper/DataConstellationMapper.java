package com.tangerineSpecter.oms.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tangerineSpecter.oms.common.query.ConstellactionQueryObject;
import com.tangerineSpecter.oms.system.domain.DataConstellation;

@Mapper
public interface DataConstellationMapper {
	int deleteByPrimaryKey(Long id);

	int insert(DataConstellation record);

	DataConstellation selectByPrimaryKey(Long id);

	List<DataConstellation> selectAll();

	int updateByPrimaryKey(DataConstellation record);

	List<DataConstellation> queryForPage(ConstellactionQueryObject qo);

	Long queryForPageCount(ConstellactionQueryObject qo);

	/**
	 * 根据创建时间查询星座名字
	 */
	List<String> queryListByCreateTime(@Param("createTime") String create_time);
}