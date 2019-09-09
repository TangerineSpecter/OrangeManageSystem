package com.tangerinespecter.oms.common.redis;

public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

    public int getExpireSeconds();
}
