package com.tangerinespecter.oms.common.security;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.List;
import java.util.Set;

public class ShiroWildcardPermission extends WildcardPermission {

    ShiroWildcardPermission(String wildcardString) {
        super(wildcardString, DEFAULT_CASE_SENSITIVE);
    }

    /**
     * implies方法就是用来校验权限通过与否的
     *
     * @param permission
     * @return
     */
    @Override
    public boolean implies(Permission permission) {
        if (!(permission instanceof ShiroWildcardPermission)) {
            return false;
        }

        List<Set<String>> targetParts = ((ShiroWildcardPermission) permission).getParts();
        String targetUrl = targetParts.get(0).iterator().next();
        String url = getParts().get(0).iterator().next();
        if (targetUrl.startsWith(url)) {
            if (targetUrl.equals(url)) {
                return true;
            }
            return targetUrl.startsWith(url.concat("/"));
        }
        return false;
    }
}
