package com.tangerinespecter.oms.system.domain.dto.statis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 健康统计数据
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthStatisInfoDto {

    /**
     * 体重
     */
    private List<BigDecimal> weights;
    /**
     * 时间
     */
    private List<String> date;
}
