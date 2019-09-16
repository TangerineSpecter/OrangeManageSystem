package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemLogQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.LoggerListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemLog;

import java.util.List;

public interface SystemLogMapper extends BaseMapper<SystemLog> {
    /**
     * 日志列表
     *
     * @param qo
     * @return
     */
    List<LoggerListDto> queryForPage(SystemLogQueryObject qo);
}
