package com.tangerinespecter.oms.job.init;

import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.system.mapper.SystemConfigMapper;
import com.tangerinespecter.oms.system.service.system.IMenuSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 初始化管理后台
 *
 * @author TangerineSpecter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitAdminManageBean implements InitializingBean {

    private final IMenuSettingService menuSettingService;
    private final SystemConfigMapper systemConfigMapper;

    @Override
    public void afterPropertiesSet() {
        log.info("[初始化管理后台数据]");
        menuSettingService.initSystemUserAdmin();
        log.info("[初始化系统配置信息]");
        SystemConstant.systemConfig = systemConfigMapper.queryLastSystemConfig();
    }

}
