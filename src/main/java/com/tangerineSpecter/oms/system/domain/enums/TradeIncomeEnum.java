package com.tangerinespecter.oms.system.domain.enums;

import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 收益状态枚举
 *
 * @version 0.5.0
 * @date 2022年05月26日00:26:51
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TradeIncomeEnum implements IBaseDbEnum {

    TRADE_STATUS_PROFIT(0, "盈利"),
    TRADE_STATUS_LOSS(1, "亏损"),
    TRADE_STATUS_FAIR(2, "持平");


    private Integer value;

    private String desc;

    /**
     * 获取盈亏状态
     *
     * @param income 收益
     * @return 收益状态
     */
    public static Integer getIncomeStatus(int income) {
        if (income > 0) {
            return TradeIncomeEnum.TRADE_STATUS_PROFIT.getValue();
        } else if (income < 0) {
            return TradeIncomeEnum.TRADE_STATUS_LOSS.getValue();
        } else {
            return TradeIncomeEnum.TRADE_STATUS_FAIR.getValue();
        }
    }
}
