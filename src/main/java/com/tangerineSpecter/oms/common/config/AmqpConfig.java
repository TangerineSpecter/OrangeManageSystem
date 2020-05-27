package com.tangerinespecter.oms.common.config;

import com.tangerinespecter.oms.job.message.MessageKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ配置
 *
 * @author TangerineSpecter
 * @version 0.4.0
 * @date 2020年05月27日17:10:05
 */
@Slf4j
@Configuration
public class AmqpConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue initQueue() {
        log.info("消息队列初始化...");
        return new Queue(MessageKeys.SYSTEM_NOTICE_QUEUE);
    }

}
