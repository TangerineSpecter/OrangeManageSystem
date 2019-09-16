package com.tangerinespecter.oms.common.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 校验
 *
 * @author TangerineSpecter
 * @date 2019年09月16日12:50:00
 */
public class CredentialMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object tokenCredentials = this.getCredentials(token);
        Object accountCredentials = this.getCredentials(info);
        return this.equals(tokenCredentials, accountCredentials);
    }
}
