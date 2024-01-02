package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.domain.vo.system.SystemConfigInfoVo;

/**
 * @author 丢失的橘子
 */
public interface ISystemConfigService {

    /**
     * 新增系统配置
     *
     * @param systemConfig 系统配置参数
     */
    void insertInfo(SystemConfigInfoVo systemConfig);

    /**
     * 获取配置信息
     *
     * @return 系统配置信息
     */
    SystemConfig configInfo();
}
