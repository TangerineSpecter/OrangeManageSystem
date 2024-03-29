package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 健康管理页面高级查询
 *
 * @author TangerineSpecter
 * @version 0.3.0
 * @date 2020年05月09日10:09:43
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserHealthQueryObject extends BaseQueryObject {

    /**
     * 起始日期
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
