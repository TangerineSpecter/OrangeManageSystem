package com.tangerinespecter.oms.job.init;

import com.tangerinespecter.oms.common.constant.CommonConstant;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.dao.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import com.tangerinespecter.oms.system.service.system.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private SystemUserService systemUserService;

    @Override
    public void afterPropertiesSet() {
        log.info("管理后台数据初始化...");
        List<SystemUser> list = systemUserMapper.selectList(null);
        if (list.size() == 0) {
            try {
                SystemUser admin = SystemUser.builder().admin(1).username("admin")
                        .password("123456").isDel(CommonConstant.IS_DEL_NO).build();
                systemUserService.insertSystemUserInfo(admin);
                log.info("超级管理员账号初始化完毕");
            } catch (Exception e) {
                log.error("超级管理员账号初始化异常，{}", e.getMessage());
            }
        }
    }
}
