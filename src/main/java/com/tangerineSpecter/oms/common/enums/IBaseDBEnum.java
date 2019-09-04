package com.tangerinespecter.oms.common.enums;

import java.util.Objects;
import java.util.Optional;

/**
 * @author TangerineSpecter
 */
public interface IBaseDbEnum {
    /**
     * 枚举的中文解释
     */
    String getDesc();

    /**
     * 获取存储在数据库中的值
     */
    Integer getValue();


    /**
     * 根据int值获取枚举类型
     *
     * @param enumType
     * @param value
     */
    static <T extends IBaseDbEnum> Optional<T> fromValue(Class<T> enumType, Integer value) {
        for (T object : enumType.getEnumConstants()) {
            if (Objects.equals(value, object.getValue())) {
                return Optional.of(object);
            }
        }
        throw new IllegalArgumentException();
    }
}
