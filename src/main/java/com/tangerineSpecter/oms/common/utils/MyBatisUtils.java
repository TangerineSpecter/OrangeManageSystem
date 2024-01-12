package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tangerinespecter.oms.common.mapper.pojo.PageParam;
import com.tangerinespecter.oms.common.mapper.pojo.SortingField;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * MyBatis 工具类
 *
 * @author 丢失的橘子
 */
public class MyBatisUtils {

    public static <T> Page<T> buildPage(PageParam pageParam) {
        return buildPage(pageParam, null);
    }

    public static <T> Page<T> buildPage(PageParam pageParam, Collection<SortingField> sortingFields) {
        // 页码 + 数量
        Page<T> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        // 排序字段
        if (!CollUtil.isEmpty(sortingFields)) {
            page.addOrder(sortingFields.stream().map(sortingField -> SortingField.ORDER_ASC.equals(sortingField.getOrder()) ?
                            OrderItem.asc(sortingField.getField()) : OrderItem.desc(sortingField.getField()))
                    .collect(Collectors.toList()));
        }
        return page;
    }

}
