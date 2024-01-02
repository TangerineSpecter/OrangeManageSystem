package com.tangerinespecter.oms.system.service.system.impl;

import com.tangerinespecter.oms.system.convert.system.SystemConvert;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.domain.vo.system.SystemConfigInfoVo;
import com.tangerinespecter.oms.system.mapper.SystemConfigMapper;
import com.tangerinespecter.oms.system.service.system.ISystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 丢失的橘子
 */
@Service
@RequiredArgsConstructor
public class SystemConfigServiceImpl implements ISystemConfigService {

    private final SystemConfigMapper systemConfigMapper;

    @Override
    public SystemConfig configInfo() {
        return systemConfigMapper.queryLastSystemConfig();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertInfo(SystemConfigInfoVo param) {
        systemConfigMapper.deleteConfigAll();
        systemConfigMapper.insert(SystemConvert.INSTANCE.convert(param));
    }

}
