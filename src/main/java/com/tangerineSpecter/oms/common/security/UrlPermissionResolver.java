package com.tangerinespecter.oms.common.security;

import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 自定义Url权限管理器
 */
@Slf4j
public class UrlPermissionResolver implements PermissionResolver {
    @Override
    public Permission resolvePermission(String permissionString) {
        boolean permitted = SecurityUtils.getSubject().isPermitted(permissionString);
        log.info("url ==>>>" + permissionString + ":result ==>>>" + permitted);
        return new WildcardPermission(permissionString);
    }
}
