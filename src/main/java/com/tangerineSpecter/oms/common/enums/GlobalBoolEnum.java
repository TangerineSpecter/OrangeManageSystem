package com.tangerinespecter.oms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 全局布尔枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum GlobalBoolEnum implements IBaseDbEnum {

    FALSE(0, "否"),
    TRUE(1, "是");

    private Integer value;

    private String desc;
}
