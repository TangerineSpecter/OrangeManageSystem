package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统日志列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggerListDto {

    private Long id;

    private String username;

    private Integer event;

    private String eventDesc;

    private String method;

    private String params;

    private String operation;

    private Long time;

    private String ip;

    private String createTime;
}
