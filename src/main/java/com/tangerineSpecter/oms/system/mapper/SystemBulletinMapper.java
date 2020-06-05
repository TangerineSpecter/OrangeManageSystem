package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemBulletin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemBulletinMapper extends BaseMapper<SystemBulletin> {
    /**
     * 获取最近的系统公告
     */
    List<SystemBulletin> queryRecentlyBulletinList();

    List<SystemBulletin> queryForPage(SystemBulletinQueryObject qo);
}
