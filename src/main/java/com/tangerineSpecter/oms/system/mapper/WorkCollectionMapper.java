package com.tangerinespecter.oms.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.system.domain.WorkCollection;

@Mapper
public interface WorkCollectionMapper {
    int insert(WorkCollection record);

    int updateByPrimaryKey(WorkCollection record);

    void delete(Long id);

    List<WorkCollection> queryForPage(WorkCollectionQueryObject qo);
}