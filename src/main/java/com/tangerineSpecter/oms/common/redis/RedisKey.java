package com.tangerinespecter.oms.common.redis;

import com.tangerinespecter.oms.common.constants.CommonConstant;

import java.io.Serializable;

/**
 * 业务缓存Key
 */
public class RedisKey extends BasePrefixKey implements Serializable {

    private RedisKey(String prefix) {
        super(prefix);
    }

    public RedisKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    /**
     * 任务执行锁（1天后自动释放）
     */
    public static final RedisKey getJobLock = new RedisKey("constellation_page:", CommonConstant.ONE_DAY);

}
