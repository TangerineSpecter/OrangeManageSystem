package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.utils.ParamUtils;
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

    /**
     * 根据地址查询最近一条菜单数据
     *
     * @param href 地址
     * @return 菜单数据
     */
    default SystemMenu selectOneByHref(String href) {
        return selectOne(new QueryWrapper<SystemMenu>()
                .eq("href", href)
                .orderByDesc("id")
                .last("limit 1"));
    }

    /**
     * 获取当前置顶数量
     *
     * @return 置顶数量
     */
    default Integer selectTopCount() {
        return selectCount(new QueryWrapper<SystemMenu>().eq(ParamUtils.TOP, CommonConstant.IS_TOP));
    }
}