package com.tangerinespecter.oms.common.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防刷注解
 *
 * @author TangerineSpecter
 * @date 2019年09月14日16:14:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AccessLimit {

    /**
     * 时间间隔，默认5秒
     */
    int seconds() default 5;

    /**
     * 最大请求次数；默认5次
     */
    int maxCount() default 5;

    /**
     * 默认需要登录
     */
    boolean needLogin() default true;
}
