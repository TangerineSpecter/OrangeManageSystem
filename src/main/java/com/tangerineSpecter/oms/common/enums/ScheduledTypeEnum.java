package com.tangerinespecter.oms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum ScheduledTypeEnum implements IBaseDbEnum {

    DEFAULT(0, "系统默认"),
    COMMON_TASK(1, "一般调度"),
    BOT_NOTIFY(2, "机器人通知");

    private Integer value;
    private String name;
}
