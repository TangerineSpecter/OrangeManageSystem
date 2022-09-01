package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.convert.system.MenuConvert;
import com.tangerinespecter.oms.system.convert.user.RoleConvert;
import com.tangerinespecter.oms.system.domain.entity.*;
import com.tangerinespecter.oms.system.domain.enums.UserStatusEnum;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;
import com.tangerinespecter.oms.system.mapper.*;
import com.tangerinespecter.oms.system.service.system.IMenuSettingService;
import com.tangerinespecter.oms.system.service.system.IPermissionManageService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 丢失的橘子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuSettingServiceImpl implements IMenuSettingService {

    private final SystemMenuMapper systemMenuMapper;
    private final SystemUserMapper systemUserMapper;
    private final ISystemUserService systemUserService;
    private final SystemRoleMapper systemRoleMapper;
    private final SystemPermissionMapper systemPermissionMapper;
    private final SystemUserRoleMapper systemUserRoleMapper;
    private final SystemPermissionRoleMapper systemPermissionRoleMapper;
    private final IPermissionManageService permissionManageService;

    @Override
    public PageInfo<SystemMenu> listInfo() {
        return new PageInfo<>(systemMenuMapper.selectList(null));
    }

    @Override
    public void deleteInfo(Long id) {
        SystemMenu systemMenu = systemMenuMapper.selectById(id);
        Assert.notNull(systemMenu, () -> new BusinessException(RetCode.SYSTEM_MENU_NOT_EXIST));
        List<SystemMenu> menuList = systemMenuMapper.selectByPid(systemMenu.getId());
        Assert.isTrue(menuList.isEmpty(), () -> new BusinessException(RetCode.SYSTEM_MENU_CHILD_EXIST));
        systemMenuMapper.deleteById(id);
        permissionManageService.deleteInfo(SystemUtils.getPermissionCode(systemMenu.getPermissionCode()));
    }

    @Override
    public void insertInfo(SystemMenuInfoVo vo) {
        Assert.isTrue(this.checkMenuHrefNotExist(null, vo.getHref()), () -> new BusinessException(RetCode.SYSTEM_MENU_HREF_EXIST));
        SystemMenu systemMenu = MenuConvert.INSTANCE.convert(vo);
        systemMenuMapper.insert(systemMenu);
        systemMenu.setPermissionCode(SystemUtils.getMenuCode(systemMenu.getHref(), systemMenu.getId()));
        systemMenuMapper.updateById(systemMenu);
        permissionManageService.init();
    }

    /**
     * 校验菜单地址是否不存在
     *
     * @param id   菜单ID
     * @param href 菜单地址
     * @return true:不存在
     */
    private boolean checkMenuHrefNotExist(Long id, String href) {
        SystemMenu menu = systemMenuMapper.selectById(id);
        //不存在，则能添加
        if (menu == null) {
            return true;
        }
        //存在，但是同一个或者id为空，则能添加
        if (id == null || Objects.equals(id, menu.getId())) {
            return true;
        }
        //地址不为空，并且不相等，则能添加
        return CharSequenceUtil.isNotEmpty(href) && !Objects.equals(href, menu.getHref());
    }

    @Override
    public SystemMenu detailInfo(Long id) {
        return systemMenuMapper.selectById(id);
    }

    @Override
    public void updateInfo(SystemMenuInfoVo vo) {
        SystemMenu systemMenu = systemMenuMapper.selectById(vo.getId());
        Assert.isTrue(this.checkMenuHrefNotExist(vo.getId(), vo.getHref()), () -> new BusinessException(RetCode.SYSTEM_MENU_HREF_EXIST));
        String beforeHref = systemMenu.getHref();
        systemMenu.setTitle(vo.getTitle());
        systemMenu.setHref(vo.getHref());
        systemMenu.setPermissionCode(SystemUtils.getMenuCode(vo.getHref(), vo.getId()));
        systemMenu.setPid(vo.getPid());
        systemMenu.setIcon(vo.getIcon());
        systemMenu.setSort(vo.getSort());
        systemMenu.setTarget(vo.getTarget());
        systemMenuMapper.updateById(systemMenu);
        //重置权限url
        SystemMenu menu = systemMenuMapper.selectById(vo.getId());
        systemPermissionMapper.updateUrlByCode(SystemUtils.getPermissionUrl(beforeHref), SystemUtils.getPermissionUrl(vo.getHref()), SystemUtils.getPermissionCode(menu.getPermissionCode()));
    }

    @Override
    public List<SystemMenu> initMenuCode() {
        List<SystemMenu> systemMenus = systemMenuMapper.selectList(null);
        systemMenus.forEach(menu -> {
            if (StrUtil.isBlank(menu.getPermissionCode())) {
                String href = menu.getHref();
                String code = SystemUtils.getMenuCode(href, menu.getId());
                menu.setPermissionCode(code);
                systemMenuMapper.updateById(menu);
            }
        });
        return systemMenus;
    }

    @Override
    public void topInfo(Long id) {
        SystemMenu menu = systemMenuMapper.selectById(id);
        Assert.notNull(menu, () -> new BusinessException(RetCode.SYSTEM_MENU_NOT_EXIST));
        //只有置顶的时候进行校验上限
        Assert.isFalse(CommonConstant.IS_NOT_TOP.equals(menu.getTop()) && systemMenuMapper.selectTopCount() >= SystemConstant.SYSTEM_MENU_TOP_COUNT_THRESHOLD, () -> new BusinessException(RetCode.SYSTEM_MENU_MORE_THAN_UPPER));
        menu.setTop(CommonConstant.IS_TOP.equals(menu.getTop()) ? CommonConstant.IS_NOT_TOP : CommonConstant.IS_TOP);
        systemMenuMapper.updateById(menu);
    }

    /**
     * 初始化超级管理员
     */
    @Override
    public void initSystemUserAdmin() {
        SystemUser systemUser = systemUserMapper.selectOneByAdmin();
        String uid = Optional.ofNullable(systemUser).map(SystemUser::getUid).orElseGet(() -> {
            SystemUser newUser = systemUserService.insertSystemUserInfo(new SystemUser("admin", "123456"));
            try {
                systemUserService.insertSystemUserInfo(newUser);
                log.info("超级管理员账号初始化完毕");
            } catch (Exception e) {
                log.error("超级管理员账号初始化异常", e);
            }
            return newUser.getUid();
        });
        initSystemAdminRole(uid);
    }

    /**
     * 初始化管理员角色
     *
     * @param uid 管理员UID
     */
    private void initSystemAdminRole(String uid) {
        Assert.isTrue(CharSequenceUtil.isNotEmpty(uid), "超级管理员账号异常");
        List<SystemRole> systemRole = systemRoleMapper.selectRoleByUid(uid);
        if (CollUtil.isNotEmpty(systemRole)) {
            return;
        }
        SystemRole createSystemRole = SystemRole.builder().name("系统管理员")
                .status(UserStatusEnum.EFFECTIVE.getValue()).remark("系统管理员").build();
        systemRoleMapper.insert(createSystemRole);
        systemUserRoleMapper.insert(RoleConvert.INSTANCE.create(uid, createSystemRole.getId()));
        initPermission(createSystemRole.getId());
    }

    /**
     * 初始化权限
     *
     * @param roleId 角色ID
     */
    private void initPermission(Long roleId) {
        Assert.isTrue(roleId != null, "超级管理员角色异常");
        List<SystemMenu> systemMenus = this.initMenuCode();
        List<SystemPermission> systemPermissions = systemPermissionMapper.selectList(null);
        List<String> permissionCodes = systemPermissions.stream().map(SystemPermission::getCode).collect(Collectors.toList());
        systemMenus.forEach(menu -> {
            //如果存在新的菜单不在权限表内
            if (!permissionCodes.contains(SystemUtils.getPermissionCode(menu.getPermissionCode()))) {
                SystemPermission permission = SystemPermission.builder().name(menu.getTitle() + "权限")
                        .code(SystemUtils.getPermissionCode(menu.getPermissionCode()))
                        .sort(0).url(SystemUtils.getPermissionUrl(menu.getHref())).remark(null).sort(0).build();
                systemPermissionMapper.insert(permission);
                SystemPermissionRole permissionRole = SystemPermissionRole.builder().rid(roleId).pid(permission.getId()).build();
                systemPermissionRoleMapper.insert(permissionRole);
            }
        });
        List<SystemPermission> havePermissions = systemPermissionMapper.selectListByRoleId(roleId);
        List<String> havePermissionCodes = havePermissions.stream().map(SystemPermission::getCode).collect(Collectors.toList());
        //获取未拥有的权限列表
        List<SystemPermission> notHavePermissionList = systemPermissions.stream().filter(p -> !havePermissionCodes.contains(p.getCode())).collect(Collectors.toList());
        notHavePermissionList.forEach(p -> {
            SystemPermissionRole permissionRole = SystemPermissionRole.builder().rid(roleId).pid(p.getId()).build();
            systemPermissionRoleMapper.insert(permissionRole);
        });
        log.info("[管理员权限初始化完毕]");
    }
}
