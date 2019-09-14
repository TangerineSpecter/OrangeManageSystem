package com.tangerinespecter.oms.common.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class AccessKey extends BasePrefixKey {

    public AccessKey(String prefix) {
        super(prefix);
    }

    /**
     * 访问限流缓存
     */
    public static AccessKey access = new AccessKey("access_:");
}
