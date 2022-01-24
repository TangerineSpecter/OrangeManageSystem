package com.tangerinespecter.oms.system.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHealthInfoVo implements Serializable {

    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 体重（斤）
     */
    private BigDecimal weight;
    /**
     * bmi值
     */
    private BigDecimal bmi;
    /**
     * 体脂率（百分比）
     */
    private BigDecimal bodyFatRate;
    /**
     * 内脏等级
     */
    private Integer viscusLevel;
    /**
     * 肌肉重量（斤）
     */
    private BigDecimal muscleWeight;
    /**
     * 脂肪量（斤）
     */
    private BigDecimal fatWeight;
    /**
     * 基础代谢率（百分比）
     */
    @Max(value = 10000, message = "代谢率超出最大合理范围")
    private Integer basalMetabolismRate;
    /**
     * 身体水分（百分比）
     */
    private BigDecimal bodyMoistureRate;
    /**
     * 身体年龄
     */
    private Integer physicalAge;
    /**
     * 去脂体重（斤）
     */
    private BigDecimal leanBodyMass;
    /**
     * 皮下脂肪（百分比）
     */
    private BigDecimal subcutaneousFatRate;
    /**
     * 骨骼肌率（百分比）
     */
    private BigDecimal skeletalMuscleRate;
    /**
     * 蛋白质率（百分比）
     */
    private BigDecimal proteinRate;
    /**
     * 骨头重量(斤)
     */
    private BigDecimal boneWeight;
    /**
     * 平均心率
     */
    private Integer heartRate;
    /**
     * 步数
     */
    private Integer stepNumber;
    /**
     * 卡路里（千卡）
     */
    private Integer calorie;
    /**
     * 压力（0~100））
     */
    private Integer pressure;
    /**
     * 睡眠时长（分钟）
     */
    private Integer sleepDuration;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
}
