package com.tangerinespecter.oms.common.constants;

import com.tangerinespecter.oms.system.domain.entity.SystemUser;

public class UserContext {

    private static ThreadLocal<SystemUser> userContext = new ThreadLocal<>();

    public static void setUserContext(SystemUser systemUser) {
        userContext.set(systemUser);
    }

    public static SystemUser getUserContext() {
        return userContext.get();
    }
}
