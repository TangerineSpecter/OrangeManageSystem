package com.tangerinespecter.oms.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 交易统计页面高级查询
 *
 * @author TangerineSpecter
 * @version v0.5.3
 * @Date 2024年01月19日18:18:19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeStatisQueryObject extends BaseQueryObject implements Serializable {

    @ApiModelProperty(value = "查询开始日期，yyyy-MM-dd")
    private String startDay;
    @ApiModelProperty(value = "查询结束日期，yyyy-MM-dd")
    private String endDay;
}
