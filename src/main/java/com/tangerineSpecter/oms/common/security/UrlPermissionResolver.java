package com.tangerinespecter.oms.common.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

/**
 * 自定义Url权限管理器
 */
@Slf4j
public class UrlPermissionResolver implements PermissionResolver {

    @Override
    public Permission resolvePermission(String permissionString) {
        return new ShiroWildcardPermission(permissionString);
    }
}
