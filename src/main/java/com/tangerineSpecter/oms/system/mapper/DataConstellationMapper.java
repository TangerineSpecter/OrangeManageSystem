package com.tangerinespecter.oms.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tangerinespecter.oms.common.query.ConstellactionQueryObject;
import com.tangerinespecter.oms.system.dao.entity.DataConstellation;

@Mapper
public interface DataConstellationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DataConstellation record);

    DataConstellation selectByPrimaryKey(Long id);

    List<DataConstellation> selectAll();

    int updateByPrimaryKey(DataConstellation record);

    List<DataConstellation> queryForPage(ConstellactionQueryObject qo);

    /**
     * 根据创建时间查询星座名字
     */
    List<String> queryListByCreateTime(@Param("createTime") String createTime);

    /**
     * 根据星座名称获取最近数据
     */
    DataConstellation getConstellationByName(@Param("starName") String name);
}