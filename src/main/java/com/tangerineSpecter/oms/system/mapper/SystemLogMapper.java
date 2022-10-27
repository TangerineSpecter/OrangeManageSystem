package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemLogQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.LoggerListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemLogMapper extends BaseMapper<SystemLog> {
    /**
     * 日志列表
     *
     * @param qo 高级查询参数
     * @return 分页列表
     */
    List<LoggerListDto> queryForPage(SystemLogQueryObject qo);
}
