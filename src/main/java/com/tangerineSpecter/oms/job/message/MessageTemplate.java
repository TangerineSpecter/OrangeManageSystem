package com.tangerinespecter.oms.job.message;

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
public class MessageTemplate {

    /**
     * 新消息通知
     */
    public static final String PUSH_NEW_MESSAGE = "收到%s条新的消息";

    public static String join(String template, Object... args) {
        return String.format(template, args);
    }

}
