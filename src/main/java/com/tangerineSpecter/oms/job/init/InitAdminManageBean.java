package com.tangerinespecter.oms.job.init;

import com.tangerinespecter.oms.system.dao.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化管理后台
 *
 * @author TangerineSpecter
 */
@Slf4j
@Component
public class InitAdminManageBean implements InitializingBean {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public void afterPropertiesSet() {
        log.info("管理后台数据初始化...");

    }
}
