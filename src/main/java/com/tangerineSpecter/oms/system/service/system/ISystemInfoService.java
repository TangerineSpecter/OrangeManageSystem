package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.system.domain.dto.system.HomePageDataDto;
import com.tangerinespecter.oms.system.domain.dto.system.StatisticsInfo;
import com.tangerinespecter.oms.system.domain.pojo.ManagerInfoBean;
import com.tangerinespecter.oms.system.domain.pojo.SystemInfoBean;

public interface ISystemInfoService {

    /**
     * 获取系统信息
     */
    SystemInfoBean getSystemInfo();

    /**
     * 获取管理员相关信息
     */
    ManagerInfoBean getManagerInfo();

    /**
     * 初始化首页
     *
     * @return
     */
    HomePageDataDto initHome();

    /**
     * 统计信息
     *
     * @return
     */
    StatisticsInfo getStatisticsInfo();
}
