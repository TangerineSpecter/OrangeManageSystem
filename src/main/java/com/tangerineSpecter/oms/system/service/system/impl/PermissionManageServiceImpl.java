package com.tangerinespecter.oms.system.service.system.impl;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.SystemPermissionQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.convert.system.PermissionConvert;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.mapper.SystemMenuMapper;
import com.tangerinespecter.oms.system.mapper.SystemPermissionMapper;
import com.tangerinespecter.oms.system.mapper.SystemPermissionRoleMapper;
import com.tangerinespecter.oms.system.service.system.IPermissionManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丢失的橘子
 */
@Service
@RequiredArgsConstructor
public class PermissionManageServiceImpl implements IPermissionManageService {

    private final SystemPermissionMapper systemPermissionMapper;
    private final SystemMenuMapper systemMenuMapper;
    private final SystemPermissionRoleMapper systemPermissionRoleMapper;

    @Override
    public List<SystemPermission> list(SystemPermissionQueryObject qo) {
        return systemPermissionMapper.queryForPage(qo);
    }

    @Override
    public void init() {
        List<String> permissionCodes = CollUtils.convertList(systemPermissionMapper.selectList(null), SystemPermission::getCode);
        CollUtils.forEach(systemMenuMapper.selectList(null), menu -> {
            //新的菜单在已有权限表内，jump
            if (permissionCodes.contains(SystemUtils.getPermissionCode(menu.getPermissionCode()))) {
                return;
            }
            systemPermissionMapper.insert(PermissionConvert.INSTANCE.convert(menu));
        });
    }

    @Override
    public void deleteInfo(String code) {
        SystemPermission systemPermission = systemPermissionMapper.queryPermissionByCode(code);
        Long id = systemPermission.getId();
        systemPermissionMapper.deleteById(id);
        systemPermissionRoleMapper.deleteByPid(id);
    }

}
