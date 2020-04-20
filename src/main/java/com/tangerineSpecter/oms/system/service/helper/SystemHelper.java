package com.tangerinespecter.oms.system.service.helper;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.system.UserPermissionListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemPermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    /**
     * 获取当前登录用户的权限列表
     */
    public List<UserPermissionListDto> getCurrentUserPermissions() {
        List<UserPermissionListDto> permissions = CollUtil.newArrayList();
        try {
            SystemUser currentUser = SystemUtils.getCurrentUser();
            Long userId = currentUser.getId();
            return systemPermissionMapper.getPermissionListByUid(userId);
        } catch (Exception ex) {
            log.error("获取当前登录用户权限列表异常");
        }
        return permissions;
    }
}
