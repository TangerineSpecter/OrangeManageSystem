package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 日志页面高级查询
 *
 * @author TangerineSpecter
 * @date 2019年09月04日21:59:26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemLogQueryObject extends BaseQueryObject {
    /**
     * 操作关键词
     */
    private String keyword;
    /**
     * 操作事件
     */
    private Integer event;
    /**
     * 操作人
     */
    private String username;
    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
