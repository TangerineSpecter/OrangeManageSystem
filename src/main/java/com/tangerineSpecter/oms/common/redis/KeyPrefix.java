package com.tangerinespecter.oms.common.redis;

public interface KeyPrefix {

    int expireSeconds();

    /**
     * @return 前缀
     */
    String getPrefix();

    /**
     * @return 过期时间（秒）
     */
    int getExpireSeconds();

    String join(Object... args);
}
