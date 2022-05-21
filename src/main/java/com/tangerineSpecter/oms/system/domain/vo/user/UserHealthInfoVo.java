package com.tangerinespecter.oms.system.domain.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author TangerineSpecter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHealthInfoVo implements Serializable {

    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 体重（斤）
     */
    @ApiModelProperty("体重（斤）")
    private BigDecimal weight;
    /**
     * bmi值
     */
    @ApiModelProperty("bmi值(0~100)")
    @Min(value = 0, message = "BMI非正常范围")
    @Max(value = 100, message = "BMI非正常范围")
    private BigDecimal bmi;
    /**
     * 体脂率（百分比）
     */
    @ApiModelProperty("体脂率（百分比）(0~100)")
    @Min(value = 0, message = "体脂率超出最小合理范围")
    @Max(value = 100, message = "体脂率超出最大合理范围")
    private BigDecimal bodyFatRate;
    /**
     * 内脏等级
     */
    @ApiModelProperty("内脏等级")
    private Integer viscusLevel;
    /**
     * 肌肉重量（斤）
     */
    @ApiModelProperty("肌肉重量（斤）")
    private BigDecimal muscleWeight;
    /**
     * 脂肪量（斤）
     */
    @ApiModelProperty("脂肪量（斤）")
    private BigDecimal fatWeight;
    /**
     * 基础代谢率（百分比）
     */
    @ApiModelProperty("基础代谢率（百分比）")
    @Min(value = 0, message = "代谢率超出最小合理范围")
    @Max(value = 100, message = "代谢率超出最大合理范围")
    private Integer basalMetabolismRate;
    /**
     * 身体水分（百分比）
     */
    @ApiModelProperty("身体水分（百分比）")
    private BigDecimal bodyMoistureRate;
    /**
     * 身体年龄
     */
    @ApiModelProperty("身体年龄")
    private Integer physicalAge;
    /**
     * 去脂体重（斤）
     */
    @ApiModelProperty("去脂体重（斤）")
    private BigDecimal leanBodyMass;
    /**
     * 皮下脂肪（百分比）
     */
    @ApiModelProperty("皮下脂肪（百分比）")
    @Min(value = 0, message = "皮下脂肪超出最小合理范围")
    @Max(value = 100, message = "皮下脂肪超出最大合理范围")
    private BigDecimal subcutaneousFatRate;
    /**
     * 骨骼肌率（百分比）
     */
    @ApiModelProperty("骨骼肌率（百分比）")
    @Min(value = 0, message = "骨骼肌率超出最小合理范围")
    @Max(value = 100, message = "骨骼肌率超出最大合理范围")
    private BigDecimal skeletalMuscleRate;
    /**
     * 蛋白质率（百分比）
     */
    @ApiModelProperty("蛋白质率（百分比）")
    @Min(value = 0, message = "蛋白质率超出最小合理范围")
    @Max(value = 100, message = "蛋白质率超出最大合理范围")
    private BigDecimal proteinRate;
    /**
     * 骨头重量(斤)
     */
    @ApiModelProperty("骨头重量(斤)")
    private BigDecimal boneWeight;
    /**
     * 平均心率
     */
    @ApiModelProperty("平均心率")
    private Integer heartRate;
    /**
     * 步数
     */
    @ApiModelProperty("步数")
    private Integer stepNumber;
    /**
     * 卡路里（千卡）
     */
    @ApiModelProperty("卡路里（千卡）")
    private Integer calorie;
    /**
     * 压力（0~100））
     */
    @ApiModelProperty("压力（0~100）")
    @Min(value = 0, message = "压力超出最小合理范围")
    @Max(value = 100, message = "压力超出最大合理范围")
    private Integer pressure;
    /**
     * 睡眠时长（分钟）
     */
    @ApiModelProperty("睡眠时长（分钟）")
    private Integer sleepDuration;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;
}
