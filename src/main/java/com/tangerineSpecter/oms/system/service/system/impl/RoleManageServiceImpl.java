package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.convert.user.RoleConvert;
import com.tangerinespecter.oms.system.domain.dto.system.SystemRoleListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.domain.entity.SystemPermissionRole;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import com.tangerinespecter.oms.system.domain.vo.system.SystemRoleInfoVo;
import com.tangerinespecter.oms.system.mapper.SystemPermissionMapper;
import com.tangerinespecter.oms.system.mapper.SystemPermissionRoleMapper;
import com.tangerinespecter.oms.system.mapper.SystemRoleMapper;
import com.tangerinespecter.oms.system.service.system.IRoleManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author 丢失的橘子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleManageServiceImpl implements IRoleManageService {

    private final SystemRoleMapper systemRoleMapper;
    private final SystemPermissionRoleMapper systemPermissionRoleMapper;
    private final SystemPermissionMapper systemPermissionMapper;

    @Override
    public List<SystemRoleListDto> list(SystemRoleQueryObject qo) {
        return this.querySystemRoleList(systemRoleMapper.queryForPage(qo));
    }

    public List<SystemRoleListDto> querySystemRoleList(List<SystemRoleListDto> pageList) {
        List<SystemPermission> systemPermissions = systemPermissionMapper.selectList(null);
        CollUtils.forEach(pageList, systemRole -> {
            List<Long> havePermissionIds = CollUtils.convertList(getRolePermission(systemRole.getId()), SystemPermission::getId);
            systemRole.initPermission(systemPermissions, havePermissionIds);
        });
        // 得到分页结果对象
        return pageList;
    }

    @Override
    public void insert(String name) {
        try {
            systemRoleMapper.insert(RoleConvert.INSTANCE.create(name));
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(RetCode.EXIST_SAME_NAME_ROLE);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        systemRoleMapper.deleteById(id);
        systemPermissionRoleMapper.deleteByRid(id);
    }

    @Override
    public Set<SystemPermission> getRolePermission(Long roleId) {
        return Optional.ofNullable(systemRoleMapper.selectRoleById(roleId))
                .map(SystemRole::getPermissions).orElse(CollUtil.newHashSet());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authorize(SystemRoleInfoVo vo) {
        try {
            systemRoleMapper.updateRoleNameById(vo.getId(), vo.getName());
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(RetCode.EXIST_SAME_NAME_ROLE);
        }
        systemPermissionRoleMapper.deleteByRid(vo.getId());
        if (CharSequenceUtil.isBlank(vo.getPermissionIds())) {
            return;
        }
        List<SystemPermissionRole> systemPermissionRoles = systemPermissionRoleMapper.selectList(new QueryWrapper<SystemPermissionRole>().eq("rid", vo.getId()));
        List<Long> alreadyPermissionIds = CollUtils.convertList(systemPermissionRoles, SystemPermissionRole::getPid);
        List<Long> addPermissionIds = CollUtils.convertList(Splitter.on(",").omitEmptyStrings().splitToList(vo.getPermissionIds()), Long::parseLong);
        Collection<Long> permissionIds = CollUtil.unionDistinct(alreadyPermissionIds, addPermissionIds);
        systemPermissionRoleMapper.batchInsert(vo.getId(), permissionIds);
    }

    @Override
    public void updateStatus(Long id) {
        SystemRole systemRole = systemRoleMapper.selectRoleById(id);
        Integer status = CommonConstant.STATUS_USABLE.equals(systemRole.getStatus()) ? CommonConstant.STATUS_FREEZE : CommonConstant.STATUS_USABLE;
        systemRoleMapper.updateRoleStatus(id, status);
    }

}
