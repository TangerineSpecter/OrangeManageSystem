package com.tangerinespecter.oms.system.mapper;

import com.tangerinespecter.oms.system.domain.entity.SystemBulletin;

import java.util.List;

public interface SystemBulletinMapper {
    /**
     * 获取最近的系统公告
     */
    List<SystemBulletin> queryRecentlyBulletinList();
}
