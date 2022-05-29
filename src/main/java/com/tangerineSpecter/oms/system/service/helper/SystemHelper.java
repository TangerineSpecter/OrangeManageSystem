package com.tangerinespecter.oms.system.service.helper;

import com.tangerinespecter.oms.common.constants.MessageConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.netty.ChatHandler;
import com.tangerinespecter.oms.job.message.MessageTemplate;
import com.tangerinespecter.oms.system.domain.dto.system.UserPermissionListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.mapper.SystemNoticeMapper;
import com.tangerinespecter.oms.system.mapper.SystemPermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 系统帮助类
 *
 * @author TangerineSpecter
 * @date 2019年08月23日01:21:56
 */
@Slf4j
@Component
public class SystemHelper {

    @Resource
    private SystemPermissionMapper systemPermissionMapper;
    @Resource
    private SystemNoticeMapper systemNoticeMapper;
    @Resource
    private ChatHandler chatHandler;

    /**
     * 获取当前登录用户的权限列表
     */
    public List<UserPermissionListDto> getCurrentUserPermissions() {
        try {
            return systemPermissionMapper.getPermissionListByUid(UserContext.getUid());
        } catch (Exception ex) {
            log.error("获取当前登录用户权限列表异常");
        }
        return Collections.emptyList();
    }

    /**
     * 推送系统通知消息
     */
    public void pushSystemNotice() {
        List<SystemNotice> systemNotices = systemNoticeMapper.selectListByUid(UserContext.getUid());
        if (systemNotices.isEmpty()) {
            return;
        }
        chatHandler.sendAllUser(MessageTemplate.PUSH_NEW_MESSAGE.join(systemNotices.size()));
//        boolean pushResult = chatHandler.sendCurrentUser(MessageTemplate.join(MessageTemplate.PUSH_NEW_MESSAGE, systemNotices.size()));
//        if (!pushResult) {
//            return;
//        }
        for (SystemNotice systemNotice : systemNotices) {
            systemNotice.setPushStatus(MessageConstant.IS_PUSH);
            systemNoticeMapper.updateById(systemNotice);
        }
    }
}
