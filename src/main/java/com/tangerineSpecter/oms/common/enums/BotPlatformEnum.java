package com.tangerinespecter.oms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum BotPlatformEnum implements IBaseDbEnum {

    FS(0, "飞书机器人"),
    WORK_WX(1, "企业微信机器人");

    private Integer value;
    private String name;
}
