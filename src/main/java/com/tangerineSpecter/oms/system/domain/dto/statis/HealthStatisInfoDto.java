package com.tangerinespecter.oms.system.domain.dto.statis;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 健康统计数据
 */
@Data
@Builder
@AllArgsConstructor
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
     * BMI数据
     */
    private List<BigDecimal> bmiData;
    /**
     * 压力数据
     */
    private List<Integer> pressureData;
    /**
     * 步数数据
     */
    private List<Integer> stepNumberData;
    /**
     * 睡眠数据(小时)
     */
    private List<BigDecimal> sleepDurationData;
    /**
     * 时间
     */
    private List<String> date;

    public HealthStatisInfoDto() {
        this.weightData = CollUtil.newArrayList();
        this.fatWeightData = CollUtil.newArrayList();
        this.bmiData = CollUtil.newArrayList();
        this.pressureData = CollUtil.newArrayList();
        this.stepNumberData = CollUtil.newArrayList();
        this.sleepDurationData = CollUtil.newArrayList();
        this.date = CollUtil.newArrayList();
    }
}
