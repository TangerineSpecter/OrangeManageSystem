package com.tangerinespecter.oms.common.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 星座页面高级查询
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @date 2019年1月9日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstellationQueryObject implements Serializable {
    /**
     * 查询星座
     */
    @ApiModelProperty("星座")
    private String star;
    /**
     * 查询日期
     */
    @ApiModelProperty("日期")
    private String queryDay;

}
