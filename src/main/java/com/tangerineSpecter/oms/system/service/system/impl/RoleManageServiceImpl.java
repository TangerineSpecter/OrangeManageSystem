package com.tangerinespecter.oms.system.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import com.tangerinespecter.oms.system.mapper.SystemRoleMapper;
import com.tangerinespecter.oms.system.service.system.IRoleManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleManageServiceImpl implements IRoleManageService {

    @Resource
    private SystemRoleMapper systemRoleMapper;

    @Override
    public ServiceResult querySystemRoleList(SystemRoleQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemRole> pageList = systemRoleMapper.queryForPage(qo);
        // 得到分页结果对象
        PageInfo<SystemRole> systemUserInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, systemUserInfo.getTotal());
    }
}
