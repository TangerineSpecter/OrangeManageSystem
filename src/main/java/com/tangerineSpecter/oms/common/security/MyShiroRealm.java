package com.tangerineSpecter.oms.common.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangerineSpecter.oms.common.constant.CommonConstant;
import com.tangerineSpecter.oms.common.constant.RetCode;
import com.tangerineSpecter.oms.common.utils.DateUtils;
import com.tangerineSpecter.oms.common.utils.SystemUtils;
import com.tangerineSpecter.oms.system.domain.SystemUser;
import com.tangerineSpecter.oms.system.mapper.SystemUserMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户认证权限校验
 * 
 * @author TangerineSpecter
 * @Datetime 2019年1月4日 12:59:45
 * @version v0.0.2
 *
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private SystemUserMapper systemUserMapper;

	/** 获取用户角色权限 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		return null;
	}

	/** 登录认证 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		log.info("用户：{}在时间{}进行了登录,登录地址{}", userName, DateUtils.getSimpleFormat(CommonConstant.DEFAULT_FORMAT_SECOND),
				SystemUtils.getLocalhostIP());

		SystemUser systemUser = systemUserMapper.getUserByUserName(userName);
		if (systemUser == null) {
			throw new UnknownAccountException(RetCode.ACCOUNTS_NOT_EXSIT_CODE_DESC);
		}
		if (!password.equals(systemUser.getPassword())) {
			throw new IncorrectCredentialsException(RetCode.ACCOUNTS_PASSWORD_ERROR_CODE_DESC);
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(systemUser, password, userName);
		return info;
	}

}
