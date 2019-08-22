package com.tangerinespecter.oms.common.security;

import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import com.tangerinespecter.oms.common.constant.CommonConstant;
import com.tangerinespecter.oms.common.constant.RetCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

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
        return null;
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
            throw new UnknownAccountException(RetCode.ACCOUNTS_NOT_EXIST.getErrorDesc());
        }
        if (!password.equals(systemUser.getPassword())) {
            throw new IncorrectCredentialsException(RetCode.ACCOUNTS_PASSWORD_ERROR.getErrorDesc());
        }
        return new SimpleAuthenticationInfo(systemUser, password, userName);
    }

}
