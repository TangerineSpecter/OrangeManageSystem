package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 交易数据页面高级查询
 *
 * @author TangerineSpecter
 * @version v0.0.6
 * @Date 2020年04月15日01:41:27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeRecordQueryObject extends BaseQueryObject implements Serializable {
    /**
     * 交易类型(0:股票；1：期货；2：外汇)
     */
    private Integer type;
    /**
     * 起始日期
     */
    private String startDay;
    /**
     * 结束日期
     */
    private String endDay;
}
