package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.util.StrUtil;
import com.tangerinespecter.oms.common.constants.CommonConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月7日
 */
public class DateUtils {

    /**
     * 默认格式
     */
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    /**
     * 星座
     */
    private static final String STAR = "摩羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手摩羯";

    /**
     * 将时间转换成指定格式 yyyy-MM-dd 精确到天
     *
     * @param date 时间
     * @return 格式化结果
     */
    public static String timeFormatToDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
        if (date != null) {
            return format.format(date);
        }
        return CommonConstant.NULL_KEY_STR;
    }

    /**
     * 获取指定格式的当前时间
     *
     * @param format 格式,如:yyyy-MM-dd
     * @return 格式化当前时间
     */
    public static String getSimpleFormat(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 根据时间获取星座
     *
     * @param date 时间 格式：yyyy-MM-dd
     * @return 星座
     */
    public static String getStarNameByDate(String date) {
        if (StrUtil.isBlank(date)) {
            return null;
        }
        String[] dateArr = date.split("-");
        if (dateArr.length != 3) {
            return null;
        }
        Integer month = Integer.valueOf(dateArr[1]);
        Integer day = Integer.valueOf(dateArr[2]);
        Integer[] arr = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
        Integer num = month * 2 - (day < arr[month - 1] ? 2 : 0);
        return STAR.substring(num, num + 2);
    }
}
