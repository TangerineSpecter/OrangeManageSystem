package com.tangerinespecter.oms.common.redis;

import com.google.common.base.Joiner;
import lombok.Data;

/**
 * Redis base key
 */
@Data
public abstract class BasePrefixKey implements KeyPrefix {

    /**
     * 过期时间
     */
    private int expireSeconds;
    /**
     * key前缀
     */
    private String prefix;

    public BasePrefixKey(String prefix) {
        this(prefix, 0);
    }

    public BasePrefixKey(String prefix, int expireSeconds) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return 0;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

    @Override
    public int getExpireSeconds() {
        return expireSeconds;
    }

    @Override
    public String join(Object... args) {
        return this.getPrefix() + Joiner.on(":").join(args);
    }
}
