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
     * 统计基本信息key + uid
     */
    public static final RedisKey TRADE_STATIS_KEY = new RedisKey("TRADE:STATIS:INFO:", CommonConstant.ONE_HOUR_BY_SECOND);
    /**
     * 任务执行锁（1天后自动释放）
     */
    public static final RedisKey JOB_LOCK = new RedisKey("constellation_page:", CommonConstant.ONE_DAY_BY_SECOND);

}
