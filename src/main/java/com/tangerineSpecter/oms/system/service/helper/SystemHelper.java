package com.tangerinespecter.oms.system.service.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.tangerinespecter.oms.common.constants.MessageConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.netty.ChatHandler;
import com.tangerinespecter.oms.job.message.MessageTemplate;
import com.tangerinespecter.oms.system.domain.dto.system.UserPermissionListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemNoticeMapper;
import com.tangerinespecter.oms.system.mapper.SystemPermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 系统帮助类
 *
 * @author TangerineSpecter
 * @date 2019年08月23日01:21:56
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemHelper {

    private final SystemPermissionMapper systemPermissionMapper;
    private final SystemNoticeMapper systemNoticeMapper;
    private final ChatHandler chatHandler;
    private final MemorySessionDAO sessionDAO;
    /**
     * 系统活跃用户session管理
     */
    public static final Map<String, Session> ACTIVE_USERS_MANAGE = MapUtil.newHashMap();

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
     * 获取在线用户列表
     *
     * @return 用户列表
     */
    public List<String> getActiveUsers() {
        List<String> result = CollUtil.newArrayList();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        for (Session session : activeSessions) {
            Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            SystemUser user = (SystemUser) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            result.add(user.getUsername());
            ACTIVE_USERS_MANAGE.put(user.getUsername(), session);
        }
        return result;
    }

    /**
     * 下线账号
     *
     * @param username 账号名
     */
    public void offline(String username) {
        final Session session = ACTIVE_USERS_MANAGE.get(username);
        Assert.isTrue(session != null, () -> new BusinessException(RetCode.ACCOUNT_NOT_LOGIN));
        sessionDAO.delete(session);
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
