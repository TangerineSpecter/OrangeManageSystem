package com.tangerinespecter.oms.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkCollectionMapper extends BaseMapper<WorkCollection> {

    List<WorkCollection> queryForPage(WorkCollectionQueryObject qo);
}