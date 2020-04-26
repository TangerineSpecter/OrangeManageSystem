package com.tangerinespecter.oms.job.init;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.*;
import com.tangerinespecter.oms.system.mapper.*;
import com.tangerinespecter.oms.system.service.system.IMenuSettingService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 初始化管理后台
 *
 * @author TangerineSpecter
 */
@Slf4j
@Component
public class InitAdminManageBean implements InitializingBean {

    @Resource
    private IMenuSettingService menuSettingService;

    @Override
    public void afterPropertiesSet() {
        log.info("管理后台数据初始化...");
        menuSettingService.initSystemUserAdmin();
    }

}
