package com.tangerinespecter.oms.system.service.system.impl;

import com.tangerinespecter.oms.common.query.SystemLogQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.LoggerListDto;
import com.tangerinespecter.oms.system.mapper.SystemLogMapper;
import com.tangerinespecter.oms.system.service.system.ISystemLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemLogServiceImpl implements ISystemLogService {

    @Resource
    private SystemLogMapper systemLogMapper;

    @Override
    public List<LoggerListDto> list(SystemLogQueryObject qo) {
        return systemLogMapper.queryForPage(qo);
    }
}
