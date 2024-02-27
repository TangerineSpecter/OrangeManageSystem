package com.tangerinespecter.oms.common.redis;

/**
 * @author 丢失的橘子
 */
public class AccessKey extends BasePrefixKey {

    public AccessKey(String prefix) {
        super(prefix);
    }

    /**
     * 访问限流缓存
     */
    public static AccessKey access = new AccessKey("access:");
}
