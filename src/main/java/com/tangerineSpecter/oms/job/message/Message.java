package com.tangerinespecter.oms.job.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private Long id;
    /**
     * 推送用户ID
     */
    private String uid;
    /**
     * 推送标题
     */
    private String title;
    /**
     * 推送内容
     */
    private String content;
    /**
     * 消息类型枚举
     */
    private MessageTypeEnum messageType;

    public Integer getType() {
        return this.messageType.getType();
    }
}
