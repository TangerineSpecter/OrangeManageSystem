package com.tangerinespecter.oms.system.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.SystemLogQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
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
    public ServiceResult queryForPage(SystemLogQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<LoggerListDto> pageList = systemLogMapper.queryForPage(qo);
        pageList.forEach(d -> d.setEventDesc(LogOperation.getDesc(d.getEvent())));
        PageInfo<LoggerListDto> loggerList = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, loggerList.getTotal());
    }
}
