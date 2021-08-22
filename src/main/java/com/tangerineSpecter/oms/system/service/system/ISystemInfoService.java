package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.system.domain.dto.system.HomePageDataDto;
import com.tangerinespecter.oms.system.domain.dto.system.MenuChildInfo;
import com.tangerinespecter.oms.system.domain.dto.system.StatisticsInfo;
import com.tangerinespecter.oms.system.domain.dto.system.SystemNoticeInfo;
import com.tangerinespecter.oms.system.domain.pojo.ManagerInfoBean;
import com.tangerinespecter.oms.system.domain.pojo.SystemInfoBean;

import java.util.List;

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
     * 收益统计信息
     *
     * @return
     */
    StatisticsInfo getStatisticsInfo();

    /**
     * 系统公告信息
     * @return
     */
    SystemNoticeInfo getNoticeInfo();

    /**
     * 初始化菜单
     * @return
     */
    List<MenuChildInfo> initMenu();
}
