package com.tangerinespecter.oms.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfigInfoVo {

    private String webTitle;

    private String webUrl;

    private Integer cacheTime;

    private Integer fileSize;

    private String fileSuffix;

    private String homeTitle;

    private String copyright;
}
