package com.tangerinespecter.oms.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 手机号校验
 *
 * @author TangerineSpecter
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {
    /**
     * 是否校验空值
     */
    boolean required() default false;

    String message() default "手机号码格式有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
