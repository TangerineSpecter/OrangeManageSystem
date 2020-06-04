package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tangerinespecter.oms.system.domain.dto.system.VersionHistoryListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemVersionHistory;
import com.tangerinespecter.oms.system.domain.entity.SystemVersionHistoryContent;
import com.tangerinespecter.oms.system.mapper.SystemVersionHistoryContentMapper;
import com.tangerinespecter.oms.system.mapper.SystemVersionHistoryMapper;
import com.tangerinespecter.oms.system.service.system.ISystemVersionHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemVersionHistoryImpl implements ISystemVersionHistoryService {

    @Resource
    private SystemVersionHistoryMapper systemVersionHistoryMapper;
    @Resource
    private SystemVersionHistoryContentMapper systemVersionHistoryContentMapper;

    @Override
    public List<VersionHistoryListDto> getVersionList() {
        List<VersionHistoryListDto> dtoList = CollUtil.newArrayList();
        QueryWrapper<SystemVersionHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("version_number");
        List<SystemVersionHistory> systemVersionHistories = systemVersionHistoryMapper.selectList(queryWrapper);
        systemVersionHistories.forEach(history -> {
            List<SystemVersionHistoryContent> historyInfos = systemVersionHistoryContentMapper.selectListByVersionId(history.getId());
            dtoList.add(new VersionHistoryListDto(history.getVersion(), history.getCreateTime(), historyInfos));
        });
        return dtoList;
    }
}
