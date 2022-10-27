package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 收藏页面高级查询
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月9日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkCollectionQueryObject extends BaseQueryObject {

    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private String type;
}
