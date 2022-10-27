package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 系统消息高级查询
 *
 * @author TangerineSpecter
 * @version 0.4.0
 * @date 2020年05月29日23:25:35
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemNoticeQueryObject extends BaseQueryObject {

    private Integer readStatus;

    private Integer isDel;

}
