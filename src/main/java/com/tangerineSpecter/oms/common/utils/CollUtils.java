package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Collection 工具类
 *
 * @author TangerineSpecter
 */
public class CollUtils {

    public static boolean containsAny(Object source, Object... targets) {
        return Arrays.asList(targets).contains(source);
    }

    public static boolean isAnyEmpty(Collection<?>... collections) {
        return Arrays.stream(collections).anyMatch(CollectionUtil::isEmpty);
    }

    public static <T> List<T> filterList(Collection<T> from, Predicate<T> predicate) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyList();
        }
        return from.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T, R> List<T> distinct(Collection<T> from, Function<T, R> keyMapper) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyList();
        }
        return distinct(from, keyMapper, (t1, t2) -> t1);
    }

    public static <T, R> List<T> distinct(Collection<T> from, Function<T, R> keyMapper, BinaryOperator<T> cover) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(convertMap(from, keyMapper, Function.identity(), cover).values());
    }

    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyList();
        }
        return from.stream().map(func).collect(Collectors.toList());
    }

    public static <T, U> List<U> convertFilterList(Collection<T> from, Predicate<T> predicate, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyList();
        }
        return from.stream().filter(predicate).map(func).collect(Collectors.toList());
    }

    public static <T, U> List<U> convertDistinctList(Collection<T> from, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyList();
        }
        return from.stream().map(func).distinct().collect(Collectors.toList());
    }

    public static <T, U> List<U> convertLimitList(Collection<T> from, Function<T, U> func, int limit) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyList();
        }
        return from.stream().map(func).limit(limit).collect(Collectors.toList());
    }

    public static <T> List<T> convertLimitList(Collection<T> from, int limit) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyList();
        }
        return from.stream().limit(limit).collect(Collectors.toList());
    }

    public static <T, U> List<U> convertListOrDefault(Collection<T> from, Function<T, U> func, U defaultValue) {
        if (CollUtil.isEmpty(from)) {
            return CollUtil.newArrayList(defaultValue);
        }
        return from.stream().map(func).collect(Collectors.toList());
    }

    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptySet();
        }
        return from.stream().map(func).collect(Collectors.toSet());
    }

    public static <T, U> Set<U> convertFilterSet(Collection<T> from, Predicate<T> predicate, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptySet();
        }
        return from.stream().filter(predicate).map(func).collect(Collectors.toSet());
    }

    public static <T, K> Map<K, T> convertMap(Collection<T> from, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }
        return convertMap(from, keyFunc, Function.identity());
    }

    public static <T, K> Map<K, T> convertMap(Collection<T> from, Function<T, K> keyFunc, Supplier<? extends Map<K, T>> supplier) {
        if (CollUtil.isEmpty(from)) {
            return supplier.get();
        }
        return convertMap(from, keyFunc, Function.identity(), supplier);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }
        return convertMap(from, keyFunc, valueFunc, mergeFunction, HashMap::new);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
        if (CollUtil.isEmpty(from)) {
            return supplier.get();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }
        return from.stream().collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction, supplier));
    }

    public static <T, K> Map<K, List<T>> convertMultiMap(Collection<T> from, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(t -> t, Collectors.toList())));
    }

    public static <T, K> Map<K, Long> convertCountMultiMap(Collection<T> from, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(t -> t, Collectors.counting())));
    }

    public static <T, K, V> Map<K, List<V>> convertMultiMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }
        return from.stream()
                .collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toList())));
    }

    public static <T, K, V> Map<K, Set<V>> convertMultiMap2(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toSet())));
    }

    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        return CollUtil.containsAny(source, candidates);
    }

    public static <T> T getFirst(List<T> from) {
        return !CollectionUtil.isEmpty(from) ? from.get(0) : null;
    }

    public static <T> T findFirst(List<T> from, Predicate<T> predicate) {
        if (CollUtil.isEmpty(from)) {
            return null;
        }
        return from.stream().filter(predicate).findFirst().orElse(null);
    }

    public static <T> void addIfNotNull(Collection<T> coll, T item) {
        if (item == null) {
            return;
        }
        coll.add(item);
    }

    public static <T> void forEach(Collection<T> coll, Consumer<T> consumer) {
        if (CollUtil.isEmpty(coll)) {
            return;
        }
        coll.forEach(consumer);
    }

}
