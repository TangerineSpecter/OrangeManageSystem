package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.system.domain.dto.system.VersionHistoryListDto;

import java.util.List;

/**
 * @author 丢失的橘子
 */
public interface ISystemVersionHistoryService {

    /**
     * 版本更新历史列表
     *
     * @return 版本更新历史数据
     */
    List<VersionHistoryListDto> getVersionList();
}
