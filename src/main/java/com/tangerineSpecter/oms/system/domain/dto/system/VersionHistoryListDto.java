package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 版本历史更新记录
 *
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionHistoryListDto implements Serializable {

    private String version;

    private String createTime;

    private List<History> historyInfos;

    @Data
    public static class History {

        private Integer type;

        private String content;
    }

}
