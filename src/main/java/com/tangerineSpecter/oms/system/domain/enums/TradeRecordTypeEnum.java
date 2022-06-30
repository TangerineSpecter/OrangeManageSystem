package com.tangerinespecter.oms.system.domain.enums;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易类型枚举类
 *
 * @author tangerineSpecter
 * @version 0.0.6
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum TradeRecordTypeEnum implements IBaseDbEnum {
    //股票
    STOCK_TYPE(0, "股票"),
    //期货
    FUTURES_TYPE(1, "期货"),
    //外汇
    FOREIGN_EXCHANGE_TYPE(2, "外汇"),
    //基金
    FUND_TYPE(3, "基金"),
    //未知类型
    UNKNOWN(-1, "未知");

    private Integer value;

    private String desc;

    /**
     * 获取所有类型
     *
     * @return
     */
    public static List<Integer> getTypes() {
        TradeRecordTypeEnum[] values = TradeRecordTypeEnum.values();
        ArrayList<Integer> typeList = CollUtil.newArrayList();
        for (TradeRecordTypeEnum typeEnum : values) {
            typeList.add(typeEnum.value);
        }
        return typeList;
    }

    /**
     * 根据类型获取枚举
     *
     * @param type 类型
     * @return
     */
    public static TradeRecordTypeEnum getType(int type) {
        for (TradeRecordTypeEnum recordTypeEnum : TradeRecordTypeEnum.values()) {
            if (recordTypeEnum.value.equals(type)) {
                return recordTypeEnum;
            }
        }
        return UNKNOWN;
    }

}
