package com.tangerinespecter.oms.common.enums;

import lombok.Getter;

/**
 * 日志事件枚举
 */
@Getter
public enum LogOperation implements IBaseDbEnum {

    EVENT_LOGIN(1, "登录"),
    EVENT_VISIT(2, "访问"),
    EVENT_ADD(3, "添加"),
    EVENT_UPDATE(4, "编辑"),
    EVENT_DELETE(5, "删除");

    private Integer value;
    private String desc;

    LogOperation(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer value) {
        for (LogOperation ele : values()) {
            if (ele.getValue().equals(value)) {
                return ele.getDesc();
            }
        }
        return null;
    }
}
