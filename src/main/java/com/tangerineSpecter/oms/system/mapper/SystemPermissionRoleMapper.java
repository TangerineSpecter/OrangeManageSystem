package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.SystemPermissionRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface SystemPermissionRoleMapper extends BaseMapper<SystemPermissionRole> {

    /**
     * 根据角色id删除角色关系
     *
     * @param rid 角色id
     */
    default void deleteByRid(Long rid) {
        QueryWrapper<SystemPermissionRole> queryWrapper = new QueryWrapper<SystemPermissionRole>().eq("rid", rid);
        delete(queryWrapper);
    }

    /**
     * 批量插入角色权限关系
     * @param rid 角色id
     * @param permissionIds 权限id列表
     */
    void batchInsert(@Param("rid") Long rid,@Param("permissionIds") Collection<Long> permissionIds);
}
