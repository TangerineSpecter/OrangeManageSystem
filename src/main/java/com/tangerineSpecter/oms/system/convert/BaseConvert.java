package com.tangerinespecter.oms.system.convert;

import com.tangerinespecter.oms.common.context.UserContext;
import org.mapstruct.Named;

/**
 * @author 丢失的橘子
 */
public interface BaseConvert {

    /**
     * 获取当前用户uid
     *
     * @return 用户id
     */
    @Named("uid")
    default String uid() {
        return UserContext.getUid();
    }
}
