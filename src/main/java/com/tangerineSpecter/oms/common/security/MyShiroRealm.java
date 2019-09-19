package com.tangerinespecter.oms.common.security;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.Permission;
import com.tangerinespecter.oms.system.domain.entity.Role;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户认证权限校验
 *
 * @author TangerineSpecter
 * @version v0.0.2
 * @date 2019年1月4日 12:59:45
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private SystemUserMapper systemUserMapper;

    /**
     * 获取用户角色权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SystemUser currentUser = systemUserMapper.selectOneByUserName(SystemUtils.getCurrentUser().getUsername());
        List<String> permissionList = new ArrayList<>();
        //获取当前用户角色
        List<String> roleNameList = new ArrayList<>();
        Set<Role> roleSet = currentUser.getRoles();
        if (!CollUtil.isEmpty(roleSet)) {
            for (Role role : roleSet) {
                roleNameList.add(role.getName());
                //获取当前角色对应的权限
                Set<Permission> permissionSet = role.getPermissions();
                if (!CollUtil.isNotEmpty(permissionSet)) {
                    for (Permission permission : permissionSet) {
                        permissionList.add(permission.getName());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleNameList);
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        log.info("用户：{}在时间{}进行了登录,登录地址{}", userName, DateUtils.getSimpleFormat(CommonConstant.DEFAULT_FORMAT_SECOND),
                SystemUtils.getLocalhostIP());
        SystemUser systemUser = systemUserMapper.getUserByUserName(userName);
        if (systemUser == null) {
            throw new UnknownAccountException(RetCode.REGISTER_ACCOUNTS_NOT_EXIST.getErrorDesc());
        }
        if (!password.equals(systemUser.getPassword())) {
            throw new IncorrectCredentialsException(RetCode.ACCOUNTS_PASSWORD_ERROR.getErrorDesc());
        }
        log.info("用户：{}在时间{}进行了登录,登录地址{}", userName, DateUtils.getSimpleFormat(CommonConstant.DEFAULT_FORMAT_SECOND),
                SystemUtils.getLocalhostIP());
        return new SimpleAuthenticationInfo(systemUser, password, userName);
    }

    /**
     * 获取权限授权信息，如果缓存中存在，则直接从缓存中获取，否则就重新获取， 登录成功后调用
     */
    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }
        //实际项目中这里可以设置缓存，从缓存中读取
        return doGetAuthorizationInfo(principals);
    }
}
