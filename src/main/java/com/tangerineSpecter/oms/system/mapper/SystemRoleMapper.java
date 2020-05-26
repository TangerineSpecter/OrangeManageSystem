package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.SystemRoleListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    List<SystemRoleListDto> queryForPage(SystemRoleQueryObject qo);

    List<SystemRole> selectListByType(@Param("type") Integer type);

    /**
     * 根据uid和角色关系查询角色
     *
     * @param uid
     * @return
     */
    SystemRole selectRoleByUid(@Param("uid") Long uid);

    SystemRole selectRoleById(@Param("id") Long roleId);

    SystemRole selectRoleByName(@Param("name") String name);

    void updateRoleStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 根据id更新角色名称
     *
     * @param id
     * @param name
     */
    void updateRoleNameById(@Param("id") Long id, @Param("name") String name);
}
