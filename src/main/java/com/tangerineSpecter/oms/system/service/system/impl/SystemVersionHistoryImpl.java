package com.tangerinespecter.oms.system.service.system.impl;

import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.convert.system.VersionConvert;
import com.tangerinespecter.oms.system.domain.dto.system.VersionHistoryListDto;
import com.tangerinespecter.oms.system.domain.pojo.SystemVersionInfo;
import com.tangerinespecter.oms.system.service.system.ISystemVersionHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemVersionHistoryImpl implements ISystemVersionHistoryService {

    @Resource
    private SystemVersionInfo versionInfo;

    @Override
    public List<VersionHistoryListDto> getVersionList() {
        List<VersionHistoryListDto> result = VersionConvert.INSTANCE.convert(versionInfo.getHistory());
        return CollUtils.forEach(result, r -> r.setHistoryInfos(CollUtils.sortList(r.getHistoryInfos(), VersionHistoryListDto.History::getType)));
    }
}
