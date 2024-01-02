package com.tangerinespecter.oms.job.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统消息
 *
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

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
