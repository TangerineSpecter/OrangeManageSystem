package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 链式计算
 *
 * @author 丢失的橘子
 */
public class NumChainCal {

    private BigDecimal value;

    public NumChainCal(Object value) {
        this.value = Convert.toBigDecimal(value);
    }

    /**
     * 计算的开始
     *
     * @param value 初始值
     * @return 计算器
     */
    public static NumChainCal startOf(Object value) {
        if (value == null) {
            return new NumChainCal(0);
        }
        return new NumChainCal(value);
    }

    /**
     * 加法
     *
     * @param otherValue 加数
     * @return 结果
     */
    public NumChainCal add(Object otherValue) {
        BigDecimal result = NumberUtil.add(this.value, Convert.toBigDecimal(otherValue));
        return new NumChainCal(result);
    }

    /**
     * 加法
     *
     * @param otherValue 加数
     * @return 结果
     */
    public NumChainCal add(BigDecimal... otherValue) {
        BigDecimal totalValue = NumberUtil.add(otherValue);
        BigDecimal result = NumberUtil.add(this.value, totalValue);
        return new NumChainCal(result);
    }

    /**
     * 减法
     *
     * @param otherValue 减数
     * @return 结果
     */
    public NumChainCal sub(Object otherValue) {
        BigDecimal result = NumberUtil.sub(this.value, Convert.toBigDecimal(otherValue));
        return new NumChainCal(result);
    }

    /**
     * 乘法
     *
     * @param otherValue 乘数
     * @return 结果
     */
    public NumChainCal mul(Object otherValue) {
        BigDecimal result = NumberUtil.mul(this.value, Convert.toBigDecimal(otherValue));
        return new NumChainCal(result);
    }

    /**
     * 除法
     *
     * @param otherValue 除数
     * @return 结果
     */
    public NumChainCal div(Object otherValue) {
        BigDecimal result = NumberUtil.div(this.value, Convert.toBigDecimal(otherValue));
        return new NumChainCal(result);
    }

    /**
     * 除法
     *
     * @param otherValue 除数
     * @param scale      – 精确度，如果为负值，取绝对值
     * @return 结果
     */
    public NumChainCal div(Object otherValue, int scale) {
        BigDecimal result = NumberUtil.div(this.value, Convert.toBigDecimal(otherValue), scale);
        return new NumChainCal(result);
    }


    /**
     * 获取int数值
     *
     * @return int结果
     */
    public Integer getInteger() {
        return Convert.toInt(this.value);
    }

    /**
     * 获取BigDecimal数值
     *
     * @return BigDecimal结果
     */
    public BigDecimal getBigDecimal() {
        return Convert.toBigDecimal(this.value).setScale(2, RoundingMode.CEILING);
    }

}
