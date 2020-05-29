package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemNoticeMapper extends BaseMapper<SystemNotice> {

    int queryNotReadNoticeCount(@Param("adminId") Long adminId);

    /**
     * 根据管理员查询
     *
     * @param adminId
     */
    List<SystemNotice> selectListByAdminId(@Param("adminId") Long adminId);

    /**
     * 根据
     *
     * @param adminId    管理员ID
     * @param readStatus 阅读状态
     * @return
     */
    List<SystemNotice> selectListByReadStatus(@Param("adminId") Long adminId, @Param("readStatus") Integer readStatus);

    List<SystemNotice> queryForPage(SystemNoticeQueryObject qo);
}
