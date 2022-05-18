package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户健康表
 *
 * @author tangerineSpecter
 * @version 0.3.0
 * @date 2020年05月05日02:34:52
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user_health")
public class UserHealth implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 体重（公斤）
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
     * 肌肉重量（公斤）
     */
    private BigDecimal muscleWeight;
    /**
     * 脂肪量（公斤）
     */
    private BigDecimal fatWeight;
    /**
     * 基础代谢率（百分比）
     */
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
     * 去脂体重（公斤）
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
     * 骨头重量(公斤)
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
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 记录时间
     */
    private String recordTime;
    /**
     * 管理员ID
     */
    private String uid;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
}
