package com.tangerinespecter.oms.common.redis;

public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();

    int getExpireSeconds();
}
