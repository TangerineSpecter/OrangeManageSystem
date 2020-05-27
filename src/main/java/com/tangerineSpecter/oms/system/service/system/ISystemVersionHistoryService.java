package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.system.domain.dto.system.VersionHistoryListDto;

import java.util.List;

public interface ISystemVersionHistoryService {

    List<VersionHistoryListDto> getVersionList();
}
