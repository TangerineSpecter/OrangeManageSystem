package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemNoticeMapper extends BaseMapper<SystemNotice> {

    int queryNotReadNoticeCount(@Param("adminId") Long adminId);
}
