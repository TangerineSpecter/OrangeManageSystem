package com.tangerinespecter.oms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum BotMsgTypeEnum implements IBaseDbEnum {

    SIMPLE(0, "简单消息");

    private Integer value;
    private String name;
}
