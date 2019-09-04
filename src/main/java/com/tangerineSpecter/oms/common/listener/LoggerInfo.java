package com.tangerinespecter.oms.common.listener;

import com.tangerinespecter.oms.common.enums.LogOperation;

import java.lang.annotation.*;

/**
 * @author TangerineSpecter
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LoggerInfo {

    /**
     * 操作描述
     */
    String value();

    /**
     * 操作事件
     */
    LogOperation event();

    /**
     * 是否排除：默认否
     */
    boolean ignore() default false;
}
