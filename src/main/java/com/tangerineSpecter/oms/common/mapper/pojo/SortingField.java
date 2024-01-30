package com.tangerinespecter.oms.common.mapper.pojo;

import java.io.Serializable;

/**
 * 排序字段 DTO
 * 类名 ing 避免和 ES SortField 重名。
 *
 * @author 丢失的橘子
 */
public class SortingField implements Serializable {

    private static final long serialVersionUID = 6161598948743592825L;

    /**
     * 顺序 - 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 顺序 - 降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 字段
     */
    private String field;
    /**
     * 顺序
     */
    private String order;

    // 空构造方法，解决反序列化
    public SortingField() {
    }

    public SortingField(String field, String order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public SortingField setField(String field) {
        this.field = field;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public SortingField setOrder(String order) {
        this.order = order;
        return this;
    }
}
