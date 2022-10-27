package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 系统公告高级查询
 *
 * @author TangerineSpecter
 * @version 0.3.0
 * @date 2020年05月08日22:25:03
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemBulletinQueryObject extends BaseQueryObject {

    /**
     * 公告标题
     */
    private String title;
}
