package com.tangerinespecter.oms.common.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Collections2;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
        List<String> permissionList = targetParts.stream().map(t -> t.iterator().next()).collect(Collectors.toList());
        List<String> requestList = getParts().stream().map(t -> t.iterator().next()).collect(Collectors.toList());
        Collection<String> intersection = CollUtil.intersection(requestList, permissionList);
        log.info("集合1：{} == 集合2：{},  交集结果：{} == {}", requestList, permissionList, intersection,intersection.size() == permissionList.size());
//        String targetUrl = targetParts.get(0).iterator().next();
//        String url = getParts().get(0).iterator().next();
//        if (targetUrl.startsWith(url)) {
//            if (targetUrl.equals(url)) {
//                return true;
//            }
//            return targetUrl.startsWith(url.concat("/"));
//        }
        //权限一致放行通过
        return intersection.size() == permissionList.size();
    }
}
