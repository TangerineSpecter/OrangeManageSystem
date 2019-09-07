package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    /**
     * 根据父ID查询子菜单
     *
     * @param pid
     * @return
     */
    List<SystemMenu> selectByPid(@Param("pid") Long pid);
}