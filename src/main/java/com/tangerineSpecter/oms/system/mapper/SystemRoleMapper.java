package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.SystemRoleListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import com.tangerinespecter.oms.system.domain.entity.SystemUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    List<SystemRoleListDto> queryForPage(SystemRoleQueryObject qo);

    List<SystemRole> selectListByType(@Param("type") Integer type);

    /**
     * 根据uid和角色关系查询角色
     *
     * @param uid 管理员id
     * @return
     */
    List<SystemRole> selectRoleByUid(@Param("uid") String uid);

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

    List<SystemRole> selectAllList();

}
