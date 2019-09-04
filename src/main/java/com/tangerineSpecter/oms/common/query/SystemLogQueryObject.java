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
public class SystemLogQueryObject extends QueryObject {
    /**
     * 操作内容
     */
    private String operation;
}
