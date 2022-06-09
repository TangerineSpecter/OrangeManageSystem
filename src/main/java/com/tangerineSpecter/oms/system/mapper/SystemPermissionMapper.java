package com.tangerinespecter.oms.system.mapper;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemPermissionQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.UserPermissionListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemPermissionMapper extends BaseMapper<SystemPermission> {

    /**
     * 获取当前用户的权限列表
     *
     * @param uid 用户ID
     * @return 权限列表
     */
    List<UserPermissionListDto> getPermissionListByUid(@Param("uid") String uid);

    /**
     * 根据角色ID获取权限列表
     *
     * @param roleId
     * @return
     */
    List<SystemPermission> selectListByRoleId(@Param("rid") Long roleId);

    /**
     * 根据before更新权限code和url
     *
     * @param beforeUrl
     * @param url
     * @param code
     */
    void updateUrlByCode(@Param("beforeUrl") String beforeUrl, @Param("url") String url, @Param("code") String code);

    /**
     * 权限列表
     *
     * @param qo
     * @return
     */
    List<SystemPermission> queryForPage(SystemPermissionQueryObject qo);

    /**
     * 根据code获取权限信息
     *
     * @param code 权限code
     * @return 权限信息
     */
    default SystemPermission queryPermissionByCode(String code) {
        if (CharSequenceUtil.isBlank(code)) {
            return new SystemPermission();
        }
        return selectOne(new QueryWrapper<SystemPermission>()
                .eq("code", code)
                .last("limit 1"));
    }
}
