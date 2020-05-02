package com.tangerinespecter.oms.system.domain.dto.statis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 收益统计数据
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeStatisIncomeInfoDto {

    /**
     * 总收益
     */
    private List<BigDecimal> totalIncome;
    /**
     * 时间
     */
    private List<String> date;
}
