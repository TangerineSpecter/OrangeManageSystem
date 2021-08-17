package com.tangerinespecter.oms.common.security;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回账号所有权限集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //todo
        return null;
    }

    /**
     * 返回账号所有角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        //todo
        return null;
    }
}
