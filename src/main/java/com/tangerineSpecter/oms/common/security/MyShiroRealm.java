package com.tangerinespecter.oms.common.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 获取用户角色权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SystemUser currentUser = systemUserMapper.selectOneByUserName(SystemUtils.getCurrentUser().getUsername());
        List<String> permissionList = new ArrayList<>();
        //获取当前用户角色
        List<String> roleNameList = new ArrayList<>();
        List<SystemRole> roleList = currentUser.getRoles();
        if (CollUtil.isNotEmpty(roleList)) {
            for (SystemRole role : roleList) {
                roleNameList.add(role.getName());
                //获取当前角色对应的权限
                Set<SystemPermission> permissionSet = role.getPermissions();
                for (SystemPermission permission : permissionSet) {
                    if (StrUtil.isNotBlank(permission.getUrl())) {
                        permissionList.add(permission.getUrl());
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
        stopPreviousSession(systemUser.getId());
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

    /**
     * 超级管理员自动获取所有权限
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        SystemUser systemUser = ((SystemUser) principals.getPrimaryPrincipal());
        //超级管理员默认拥有所有权限
        if (SystemConstant.IS_SYSTEM_ADMIN.equals(systemUser.getAdmin())) {
            return true;
        }
        return isPermitted(principals, getPermissionResolver().resolvePermission(permission));
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        AuthorizationInfo info = getAuthorizationInfo(principals);
        Collection<Permission> perms = getPermissions(info);
        if (CollectionUtils.isEmpty(perms)) {
            return false;
        }

        for (Permission perm : perms) {
            if (perm.implies(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 踢掉上一个登录用户
     *
     * @param id
     */
    private void stopPreviousSession(Long id) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        Session currentSession = SecurityUtils.getSubject().getSession();
        Serializable currentSessionId = currentSession.getId();
        for (Session session : sessions) {
            Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection collection = (SimplePrincipalCollection) attribute;
            if (collection == null) {
                continue;
            }
            SystemUser user = (SystemUser) collection.getPrimaryPrincipal();
            if (id.equals(user.getId())) {
                if (currentSessionId.equals(session.getId())) {
                    continue;
                }
                session.stop();
                break;
            }
        }
    }
}
