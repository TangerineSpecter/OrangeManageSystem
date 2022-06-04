package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

/**
 * 链式计算
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
     * 获取int数值
     *
     * @return int结果
     */
    public Integer getInteger() {
        return Convert.toInt(this.value);
    }

    public static void main(String[] args) {
        final Integer integer = NumChainCal.startOf(1).add(2).getInteger();
        System.out.println(integer);
    }
}
