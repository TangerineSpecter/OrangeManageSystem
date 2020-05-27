package com.tangerinespecter.oms.job.message;

import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemNoticeMapper;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统通知接受者
 *
 * @author TangerineSpecter
 * @version 0.4.0
 * @date 2020年05月27日17:40:58
 */
@Slf4j
@Component
@RabbitListener(queues = MessageKeys.SYSTEM_NOTICE_QUEUE)
public class SystemNoticeReceiver {

    @Resource
    private SystemNoticeMapper systemNoticeMapper;
    @Resource
    private SystemUserMapper systemUserMapper;

    @RabbitHandler
    public void process(Message message) {
        Long uid = message.getUid();
        SystemUser systemUser = systemUserMapper.selectById(uid);
        if (systemUser == null) {
            log.warn("消息推送异常,管理员[{}]不存在", uid);
            return;
        }
        SystemNotice systemNotice = SystemNotice.builder().title(message.getTitle()).content(message.getContent())
                .readStatus(0).adminId(uid).type(message.getType()).isDel(CommonConstant.IS_DEL_NO).build();
        systemNoticeMapper.insert(systemNotice);
    }
}
