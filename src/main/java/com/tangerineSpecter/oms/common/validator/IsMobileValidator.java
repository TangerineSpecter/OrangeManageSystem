package com.tangerinespecter.oms.common.validator;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号校验
 *
 * @author TangerineSpedter
 */
@Slf4j
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    /**
     * 默认不校验
     */
    private boolean required = false;

    /**
     * 初始化
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return isMobile(value);
        }
        if (StrUtil.isBlank(value)) {
            return true;
        }
        return isMobile(value);
    }

    /**
     * 校验手机号
     *
     * @param phone 手机号
     */
    private static boolean isMobile(String phone) {
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

}
