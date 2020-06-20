package com.tangerinespecter.oms.system.domain.enums;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易类型枚举类
 *
 * @author tangerineSpecter
 * @version 0.0.6
 */
public enum TradeRecordTypeEnum {
    //股票
    STOCK_TYPE(0, "股票"),
    //期货
    FUTURES_TYPE(1, "期货"),
    //外汇
    FOREIGN_EXCHANGE_TYPE(2, "外汇"),
    //基金
    FUND_TYPE(3, "基金");;

    private Integer type;

    private String desc;

    TradeRecordTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * 获取所有类型
     *
     * @return
     */
    public static List<Integer> getTypes() {
        TradeRecordTypeEnum[] values = TradeRecordTypeEnum.values();
        ArrayList<Integer> typeList = CollUtil.newArrayList();
        for (TradeRecordTypeEnum typeEnum : values) {
            typeList.add(typeEnum.type);
        }
        return typeList;
    }

    public Integer getType() {
        return this.type;
    }
}
