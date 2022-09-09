package com.tangerinespecter.oms.system.convert;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
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

    /**
     * 格式化时间为yyyy-MM-dd
     *
     * @param date 时间，字符串
     * @return 格式化结果
     */
    @Named("formatDate")
    default String formatDate(String date) {
        DateTime dateTime = DateUtil.parseDateTime(date);
        return DateUtil.formatDate(dateTime);
    }
}
