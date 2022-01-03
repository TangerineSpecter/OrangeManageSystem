package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.SystemUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRole> {

    /**
     * 根据uid获取帐号拥有的角色id列表
     *
     * @param uid 用户id
     * @return 角色id列表
     */
    default Set<Long> getHaveRoleIdsByUid(Long uid) {
        if (uid == null) {
            return Collections.emptySet();
        }
        List<SystemUserRole> haveRoles = selectList(new QueryWrapper<SystemUserRole>()
                .eq("uid", uid));
        return haveRoles.stream().distinct().map(SystemUserRole::getId).collect(Collectors.toSet());
    }
}
