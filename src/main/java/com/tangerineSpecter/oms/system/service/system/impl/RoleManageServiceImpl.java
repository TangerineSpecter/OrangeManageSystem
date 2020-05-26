package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemRoleListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.domain.entity.SystemPermissionRole;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import com.tangerinespecter.oms.system.domain.vo.system.SystemRoleInfoVo;
import com.tangerinespecter.oms.system.mapper.SystemPermissionMapper;
import com.tangerinespecter.oms.system.mapper.SystemPermissionRoleMapper;
import com.tangerinespecter.oms.system.mapper.SystemRoleMapper;
import com.tangerinespecter.oms.system.service.system.IRoleManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleManageServiceImpl implements IRoleManageService {

    @Resource
    private SystemRoleMapper systemRoleMapper;
    @Resource
    private SystemPermissionRoleMapper systemPermissionRoleMapper;
    @Resource
    private SystemPermissionMapper systemPermissionMapper;

    @Override
    public ServiceResult querySystemRoleList(SystemRoleQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemRoleListDto> pageList = systemRoleMapper.queryForPage(qo);
        List<SystemPermission> systemPermissions = systemPermissionMapper.selectList(null);
        for (SystemRoleListDto systemRole : pageList) {
            systemRole.setPermissions(systemPermissions);
            Set<SystemPermission> rolePermission = getRolePermission(systemRole.getId());
            List<Long> havePermissionIds = rolePermission.stream().map(SystemPermission::getId).collect(Collectors.toList());
            systemRole.setHavePermissionIds(havePermissionIds);
        }
        // 得到分页结果对象
        PageInfo<SystemRoleListDto> systemUserInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, systemUserInfo.getTotal());
    }

    @Override
    public ServiceResult insert(String name) {
        if (StrUtil.isBlank(name)) {
            return ServiceResult.paramError();
        }
        SystemRole systemRole = systemRoleMapper.selectRoleByName(name);
        if (systemRole != null) {
            return ServiceResult.error(RetCode.EXIST_SAME_NAME_ROLE);
        }
        SystemRole role = SystemRole.builder().name(name)
                .status(CommonConstant.STATUS_USABLE).build();
        systemRoleMapper.insert(role);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult update(SystemRoleInfoVo vo) {
        return ServiceResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult delete(Long id) {
        systemRoleMapper.deleteById(id);
        QueryWrapper<SystemPermissionRole> queryWrapper = new QueryWrapper<SystemPermissionRole>()
                .eq("rid", id);
        systemPermissionRoleMapper.delete(queryWrapper);
        return ServiceResult.success();
    }

    @Override
    public Set<SystemPermission> getRolePermission(Long roleId) {
        if (roleId == null) {
            return CollUtil.newHashSet();
        }
        SystemRole systemRole = systemRoleMapper.selectRoleById(roleId);
        return systemRole.getPermissions();
    }

    @Override
    public ServiceResult authorize(SystemRoleInfoVo vo) {
        if (vo.getId() == null) {
            return ServiceResult.paramError();
        }
        systemRoleMapper.updateRoleNameById(vo.getId(), vo.getName());
        QueryWrapper<SystemPermissionRole> queryWrapper = new QueryWrapper<SystemPermissionRole>()
                .eq("rid", vo.getId());
        systemPermissionRoleMapper.delete(queryWrapper);
        if (StrUtil.isBlank(vo.getPermissionIds())) {
            return ServiceResult.success();
        }
        List<SystemPermissionRole> systemPermissionRoles = systemPermissionRoleMapper.selectList(queryWrapper);
        List<Long> alreadyPermissionIds = systemPermissionRoles.stream().map(SystemPermissionRole::getPid).collect(Collectors.toList());
        List<Long> addPermissionIds = Splitter.on(",").omitEmptyStrings().splitToList(vo.getPermissionIds()).parallelStream().map(Long::parseLong)
                .collect(Collectors.toList());
        Collection<Long> list = CollUtil.union(alreadyPermissionIds, addPermissionIds);
        Set<Long> permissionIds = CollUtil.newHashSet(list);
        for (Long permissionId : permissionIds) {
            systemPermissionRoleMapper.insert(
                    SystemPermissionRole.builder()
                            .rid(vo.getId())
                            .pid(permissionId)
                            .build());
        }
        return ServiceResult.success();
    }

    @Override
    public ServiceResult updateStatus(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        SystemRole systemRole = systemRoleMapper.selectRoleById(id);
        Integer status = CommonConstant.STATUS_USABLE.equals(systemRole.getStatus()) ? CommonConstant.STATUS_FREEZE : CommonConstant.STATUS_USABLE;
        systemRoleMapper.updateRoleStatus(id, status);
        return ServiceResult.success();
    }

}
