package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    List<SystemRole> queryForPage(SystemRoleQueryObject qo);

    List<SystemRole> selectListByType(@Param("type") Integer type);

    /**
     * 根据uid和角色关系查询角色
     *
     * @param uid
     * @return
     */
    SystemRole selectRoleByUid(@Param("uid") Long uid);
}
