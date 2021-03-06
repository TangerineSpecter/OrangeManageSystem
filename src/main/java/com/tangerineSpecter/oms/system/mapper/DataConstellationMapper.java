package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataConstellationMapper extends BaseMapper<DataConstellation> {


    List<DataConstellation> queryForPage(ConstellationQueryObject qo);

    /**
     * 根据创建时间查询星座名字
     */
    List<String> queryListByCreateTime(@Param("createTime") String createTime);

    /**
     * 根据星座名称获取最近数据
     */
    DataConstellation getConstellationByName(@Param("starName") String name);
}