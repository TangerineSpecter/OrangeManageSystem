package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.system.domain.dto.system.*;
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
     * @return 首页信息
     */
    HomePageDataDto initHome();

    /**
     * 收益统计信息
     *
     * @return 收益数据
     */
    StatisticsInfo getStatisticsInfo();

    /**
     * 系统公告信息
     *
     * @return 公告信息
     */
    SystemNoticeInfo getNoticeInfo();

    /**
     * 初始化菜单
     *
     * @return 菜单列表
     */
    List<MenuChildInfo> initMenu();

    /**
     * 初始化菜单
     *
     * @return 消息列表
     */
    List<MessageDto> message();
}
