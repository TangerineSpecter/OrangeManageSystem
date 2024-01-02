package com.tangerinespecter.oms.common.enums;

import java.util.Objects;
import java.util.Optional;

/**
 * @author TangerineSpecter
 */
public interface IBaseDbEnum {

    /**
     * 获取存储在数据库中的值
     */
    Integer getValue();


    /**
     * 根据int值获取枚举
     *
     * @param enumType 枚举类型
     * @param value    枚举值
     * @return 枚举
     */
    static <T extends IBaseDbEnum> T fromValue(Class<T> enumType, Integer value) {
        for (T object : enumType.getEnumConstants()) {
            if (Objects.equals(value, object.getValue())) {
                return Optional.of(object).get();
            }
        }
        throw new IllegalArgumentException();
    }
}
