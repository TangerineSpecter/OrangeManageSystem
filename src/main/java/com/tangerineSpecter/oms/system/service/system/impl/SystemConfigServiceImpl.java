package com.tangerinespecter.oms.system.service.system.impl;

import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.domain.vo.system.SystemConfigInfoVo;
import com.tangerinespecter.oms.system.mapper.SystemConfigMapper;
import com.tangerinespecter.oms.system.service.system.ISystemConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SystemConfigServiceImpl implements ISystemConfigService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Override
    public SystemConfig configInfo() {
        return systemConfigMapper.queryLastSystemConfig();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult insertInfo(SystemConfigInfoVo vo) {
        systemConfigMapper.deleteConfigAll();
        SystemConfig config = new SystemConfig();
        BeanUtils.copyProperties(vo, config);
        systemConfigMapper.insert(config);
        SystemConstant.systemConfig = config;
        return ServiceResult.success();
    }

}
