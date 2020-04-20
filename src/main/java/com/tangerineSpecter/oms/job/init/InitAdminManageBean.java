package com.tangerinespecter.oms.job.init;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.system.domain.entity.*;
import com.tangerinespecter.oms.system.mapper.*;
import com.tangerinespecter.oms.system.service.system.IMenuSettingService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 初始化管理后台
 *
 * @author TangerineSpecter
 */
@Slf4j
@Component
public class InitAdminManageBean implements InitializingBean {

    @Resource
    private SystemUserMapper systemUserMapper;
    @Resource
    private ISystemUserService systemUserService;
    @Resource
    private SystemRoleMapper systemRoleMapper;
    @Resource
    private SystemPermissionModuleMapper permissionModuleMapper;
    @Resource
    private SystemPermissionMapper systemPermissionMapper;
    @Resource
    private SystemPermissionRoleMapper permissionRoleMapper;
    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;
    @Resource
    private IMenuSettingService menuSettingService;

    @Override
    public void afterPropertiesSet() {
        log.info("管理后台数据初始化...");
        initSystemUserAdmin();
    }

    /**
     * 初始化超级管理员
     *
     * @return
     */
    private void initSystemUserAdmin() {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<SystemUser>().eq("admin", SystemConstant.IS_SYSTEM_ADMIN)
                .eq("is_del", CommonConstant.IS_DEL_NO);
        SystemUser systemUser = systemUserMapper.selectOne(queryWrapper);
        Long adminId = null;
        if (systemUser == null) {
            try {
                systemUser = SystemUser.builder().admin(1).username("admin")
                        .password("123456").isDel(CommonConstant.IS_DEL_NO).build();
                systemUserService.insertSystemUserInfo(systemUser);
                log.info("超级管理员账号初始化完毕");
                adminId = systemUser.getId();
            } catch (Exception e) {
                log.error("超级管理员账号初始化异常，{}", e);
            }
        } else {
            adminId = systemUser.getId();
        }
        initSystemAdminRole(adminId);
    }

    /**
     * 初始化管理员角色
     *
     * @return 管理员角色ID
     */
    private void initSystemAdminRole(Long adminId) {
        if (adminId == null) {
            log.warn("初始化管理员角色异常");
            return;
        }
        SystemRole systemRole = systemRoleMapper.selectRoleByUid(adminId);
        Long adminRoleId = null;
        if (systemRole == null) {
            SystemRole createSystemRole = SystemRole.builder().name("系统管理员").type(SystemConstant.IS_SYSTEM_ADMIN_ROLE)
                    .status(SystemConstant.IS_EFFECTIVE).remark("系统管理员").build();
            systemRoleMapper.insert(createSystemRole);
            adminRoleId = createSystemRole.getId();
            SystemUserRole userRole = SystemUserRole.builder().uid(adminId).rid(adminRoleId).build();
            systemUserRoleMapper.insert(userRole);
        } else {
            adminRoleId = systemRole.getId();
        }
        initPermissionModule(adminId, adminRoleId);
    }

    /**
     * 初始化权限模块
     *
     * @param adminId
     * @param roleId
     * @return
     */
    private void initPermissionModule(Long adminId, Long roleId) {
        if (adminId == null || roleId == null) {
            log.warn("超级管理员权限模块初始化异常,adminId:[{}],roleId:[{}]", adminId, roleId);
            return;
        }
        Long moduleId;
        QueryWrapper<SystemPermissionRole> queryWrapper = new QueryWrapper<SystemPermissionRole>()
                .eq("rid", roleId);
        SystemPermissionRole systemPermissionRole = permissionRoleMapper.selectOne(queryWrapper);
        if (systemPermissionRole == null) {
            SystemPermissionModule module = SystemPermissionModule.builder().name("超级管理员权限").parentId(null)
                    .level(0).status(SystemConstant.IS_EFFECTIVE).remark(null).sort(99999).build();
            permissionModuleMapper.insert(module);
            SystemPermissionRole permissionRole = SystemPermissionRole.builder().rid(roleId).moduleId(module.getId()).build();
            permissionRoleMapper.insert(permissionRole);
            moduleId = module.getId();
        } else {
            moduleId = systemPermissionRole.getModuleId();
        }
        initPermission(moduleId);
    }

    /**
     * 初始化权限
     *
     * @param moduleId 权限模块ID
     */
    private void initPermission(Long moduleId) {
        if (moduleId == null) {
            log.warn("超级管理员权限初始化异常");
            return;
        }
        List<SystemMenu> systemMenus = menuSettingService.initMenuCode();
        QueryWrapper<SystemPermission> queryWrapper = new QueryWrapper<SystemPermission>()
                .eq("module_id", moduleId);
        List<SystemPermission> systemPermissions = systemPermissionMapper.selectList(queryWrapper);
        List<String> permissionCodes = systemPermissions.stream().map(SystemPermission::getCode).collect(Collectors.toList());
        systemMenus.forEach(menu -> {
            if (!permissionCodes.contains(SecureUtil.md5(menu.getPermissionCode() + CommonConstant.PERMISSION_CODE))) {
                SystemPermission permission = SystemPermission.builder().name(menu.getTitle() + "权限")
                        .code(SecureUtil.md5(menu.getPermissionCode() + CommonConstant.PERMISSION_CODE))
                        .moduleId(moduleId).sort(0).url(menu.getHref()).type(0).status(0).remark(null).sort(0).build();
                systemPermissionMapper.insert(permission);
            }
        });
        log.info("管理员权限初始化完毕");
    }
}
