package com.tangerinespecter.oms.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum BotMsgTypeEnum implements IBaseDbEnum {

    SIMPLE(0, "简单文本消息"),
    SIMPLE_MARKDOWN(1,"简单markdown消息");

    private Integer value;
    private String name;
}
