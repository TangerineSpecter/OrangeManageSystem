package com.tangerinespecter.oms.common.anno;

import java.lang.annotation.*;

/**
 * 重写返回体 包装结果
 *
 * @author TangerineSpecter
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReWriteBody {
}
