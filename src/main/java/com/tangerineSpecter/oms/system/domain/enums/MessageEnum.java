package com.tangerinespecter.oms.system.domain.enums;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import com.tangerinespecter.oms.system.domain.dto.system.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 消息枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum MessageEnum implements IBaseDbEnum {

    ALL_NOTICE(1, "全部消息"),
    NOT_READ_NOTICE(2, "未读消息"),
    SYSTEM_NOTICE(3, "系统消息");

    private Integer value;

    private String desc;

    /**
     * 初始化消息体
     *
     * @return 消息模型列表
     */
    public static List<MessageDto> initMessageList() {
        List<MessageDto> result = CollUtil.newArrayList();
        for (MessageEnum messageEnum : MessageEnum.values()) {
            result.add(new MessageDto(messageEnum.getValue(), messageEnum.getDesc(), null));
        }
        return result;
    }
}
