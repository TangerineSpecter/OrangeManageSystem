package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.system.domain.entity.SystemVersionHistoryContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionHistoryListDto {

    private Long id;

    private String version;

    private String createTime;

    private List<SystemVersionHistoryContent> historyInfos;

    public VersionHistoryListDto(String version, List<SystemVersionHistoryContent> historyInfos) {
        this.version = version;
        this.historyInfos = historyInfos;
    }
}
