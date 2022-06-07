package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ParamUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.convert.user.RoleConvert;
import com.tangerinespecter.oms.system.domain.entity.*;
import com.tangerinespecter.oms.system.domain.enums.UserStatusEnum;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;
import com.tangerinespecter.oms.system.mapper.*;
import com.tangerinespecter.oms.system.service.system.IMenuSettingService;
import com.tangerinespecter.oms.system.service.system.IPermissionManageService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuSettingServiceImpl implements IMenuSettingService {

    @Resource
    private SystemMenuMapper systemMenuMapper;
    @Resource
    private SystemUserMapper systemUserMapper;
    @Resource
    private ISystemUserService systemUserService;
    @Resource
    private SystemRoleMapper systemRoleMapper;
    @Resource
    private SystemPermissionMapper systemPermissionMapper;
    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;
    @Resource
    private SystemPermissionRoleMapper systemPermissionRoleMapper;
    @Resource
    private IMenuSettingService menuSettingService;
    @Resource
    private IPermissionManageService permissionManageService;

    @Override
    public ServiceResult<Object> listInfo() {
        List<SystemMenu> list = systemMenuMapper.selectList(null);
        return ServiceResult.pageSuccess(list, (long) list.size());
    }

    @Override
    public ServiceResult deleteInfo(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        SystemMenu systemMenu = systemMenuMapper.selectById(id);
        if (systemMenu == null) {
            return ServiceResult.error(RetCode.SYSTEM_MENU_NOT_EXIST);
        }
        List<SystemMenu> menuList = systemMenuMapper.selectByPid(systemMenu.getId());
        if (menuList.size() > 0) {
            return ServiceResult.error(RetCode.SYSTEM_MENU_CHILD_EXIST);
        }
        systemMenuMapper.deleteById(id);
        permissionManageService.deleteInfo(SystemUtils.getPermissionCode(systemMenu.getPermissionCode()));
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertInfo(SystemMenuInfoVo vo) {
        if (checkMenuHrefExist(null, vo.getHref())) {
            return ServiceResult.error(RetCode.SYSTEM_MENU_HREF_EXIST);
        }
        SystemMenu systemMenu = SystemMenu.builder().title(vo.getTitle()).href(vo.getHref())
                .icon(vo.getIcon()).level(vo.getLevel()).pid(vo.getPid())
                .target(vo.getTarget()).sort(vo.getSort()).build();
        systemMenuMapper.insert(systemMenu);
        systemMenu.setPermissionCode(SystemUtils.getMenuCode(systemMenu.getHref(), systemMenu.getId()));
        systemMenuMapper.updateById(systemMenu);
        permissionManageService.init();
        return ServiceResult.success();
    }

    /**
     * 校验菜单地址是否存在
     *
     * @param id   菜单ID
     * @param href 菜单地址
     * @return true:存在
     */
    private boolean checkMenuHrefExist(Long id, String href) {
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("href", href);
        SystemMenu selectMenu = systemMenuMapper.selectOne(queryWrapper);
        if (id != null) {
            if (selectMenu == null) {
                return false;
            }
            return !id.equals(selectMenu.getId());
        }
        return selectMenu != null;
    }

    @Override
    public ServiceResult detailInfo(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        SystemMenu systemMenu = systemMenuMapper.selectById(id);
        return ServiceResult.success(systemMenu);
    }

    @Override
    public ServiceResult updateInfo(SystemMenuInfoVo vo) {
        SystemMenu systemMenu = systemMenuMapper.selectById(vo.getId());
        Assert.isTrue(!checkMenuHrefExist(vo.getId(), vo.getHref()), () -> new BusinessException(RetCode.SYSTEM_MENU_HREF_EXIST));
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
        return ServiceResult.success();
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
    public ServiceResult topInfo(Long id) {
        SystemMenu menu = systemMenuMapper.selectById(id);
        if (menu == null) {
            return ServiceResult.error(RetCode.SYSTEM_MENU_NOT_EXIST);
        }
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<SystemMenu>().eq(ParamUtils.TOP, CommonConstant.IS_TOP);
        Integer count = systemMenuMapper.selectCount(queryWrapper);
        if (CommonConstant.IS_NOT_TOP.equals(menu.getTop()) && count >= SystemConstant.SYSTEM_MENU_TOP_COUNT_THRESHOLD) {
            return ServiceResult.error(RetCode.SYSTEM_MENU_MORE_THAN_UPPER);
        }
        menu.setTop(CommonConstant.IS_TOP.equals(menu.getTop()) ? CommonConstant.IS_NOT_TOP : CommonConstant.IS_TOP);
        systemMenuMapper.updateById(menu);
        return ServiceResult.success();
    }

    /**
     * 初始化超级管理员
     */
    @Override
    public void initSystemUserAdmin() {
        final SystemUser systemUser = systemUserMapper.selectOneByAdmin();
        String uid = Optional.ofNullable(systemUser).map(SystemUser::getUid).orElseGet(() -> {
            SystemUser newUser = SystemUser.builder().admin(GlobalBoolEnum.YES.getValue()).username("admin")
                    .password("123456").isDel(GlobalBoolEnum.YES.getValue()).build();
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
        List<SystemMenu> systemMenus = menuSettingService.initMenuCode();
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
