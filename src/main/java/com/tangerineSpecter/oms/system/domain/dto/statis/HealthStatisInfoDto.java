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
     * 体重数据
     */
    private List<BigDecimal> weightData;
    /**
     * 脂肪数据
     */
    private List<BigDecimal> fatWeightData;
    /**
     * 压力数据
     */
    private List<Integer> pressureData;
    /**
     * 步数数据
     */
    private List<Integer> stepNumberData;
    /**
     * 睡眠数据
     */
    private List<BigDecimal> sleepDurationData;
    /**
     * 时间
     */
    private List<String> date;
}
