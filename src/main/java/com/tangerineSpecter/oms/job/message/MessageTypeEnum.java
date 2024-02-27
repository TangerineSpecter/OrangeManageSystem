package com.tangerinespecter.oms.job.message;

/**
 * 消息类型枚举
 */
public enum MessageTypeEnum {
    /**
     * 系统通知
     */
    SYSTEM_NOTICE(0, "系统通知");

    private Integer type;

    private String desc;

    MessageTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return this.type;
    }
}
