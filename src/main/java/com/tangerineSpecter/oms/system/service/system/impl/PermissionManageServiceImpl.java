package com.tangerinespecter.oms.system.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.SystemPermissionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.mapper.SystemMenuMapper;
import com.tangerinespecter.oms.system.mapper.SystemPermissionMapper;
import com.tangerinespecter.oms.system.service.system.IPermissionManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionManageServiceImpl implements IPermissionManageService {

    @Resource
    private SystemPermissionMapper systemPermissionMapper;
    @Resource
    private SystemMenuMapper systemMenuMapper;

    @Override
    public ServiceResult queryForPage(SystemPermissionQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemPermission> pageList = systemPermissionMapper.queryForPage(qo);
        // 得到分页结果对象
        PageInfo<SystemPermission> systemUserInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, systemUserInfo.getTotal());
    }

    @Override
    public ServiceResult init() {
        List<SystemMenu> systemMenus = systemMenuMapper.selectList(null);
        List<SystemPermission> systemPermissions = systemPermissionMapper.selectList(null);
        List<String> permissionCodes = systemPermissions.stream().map(SystemPermission::getCode).collect(Collectors.toList());
        systemMenus.forEach(menu -> {
            //如果存在新的菜单不在权限表内
            if (!permissionCodes.contains(SystemUtils.getPermissionCode(menu.getPermissionCode()))) {
                SystemPermission permission = SystemPermission.builder().name(menu.getTitle() + "权限")
                        .code(SystemUtils.getPermissionCode(menu.getPermissionCode()))
                        .sort(0).url(SystemUtils.getPermissionUrl(menu.getHref())).remark(null).sort(0).build();
                systemPermissionMapper.insert(permission);
            }
        });
        return ServiceResult.success();
    }
}
