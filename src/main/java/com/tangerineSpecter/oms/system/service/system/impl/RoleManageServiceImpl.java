package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemPermissionRole;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import com.tangerinespecter.oms.system.domain.vo.system.SystemRoleInfoVo;
import com.tangerinespecter.oms.system.mapper.SystemPermissionRoleMapper;
import com.tangerinespecter.oms.system.mapper.SystemRoleMapper;
import com.tangerinespecter.oms.system.service.system.IRoleManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class RoleManageServiceImpl implements IRoleManageService {

    @Resource
    private SystemRoleMapper systemRoleMapper;
    @Resource
    private SystemPermissionRoleMapper systemPermissionRoleMapper;

    @Override
    public ServiceResult querySystemRoleList(SystemRoleQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemRole> pageList = systemRoleMapper.queryForPage(qo);
        // 得到分页结果对象
        PageInfo<SystemRole> systemUserInfo = new PageInfo<>(pageList);
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
    public ServiceResult getRolePermission(Long roleId) {
        if (roleId == null) {
            return ServiceResult.paramError();
        }
        SystemRole systemRole = systemRoleMapper.selectRoleById(roleId);
        return ServiceResult.success(systemRole.getPermissions());
    }

    @Override
    public ServiceResult authorize(SystemRoleInfoVo vo) {
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
