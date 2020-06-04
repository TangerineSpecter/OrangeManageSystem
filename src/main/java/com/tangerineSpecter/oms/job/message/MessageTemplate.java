package com.tangerinespecter.oms.job.message;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 消息模板
 *
 * @author TangerineSpecter
 * @version 0.4.0
 * @date 2020年05月29日11:46:27
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MessageTemplate {

    /**
     * 新消息通知
     */
    PUSH_NEW_MESSAGE("收到%s条新的消息");

    /**
     * 消息
     */
    private String message;


    public String join(Object... args) {
        return this.message = String.format(this.message, args);
    }

}
