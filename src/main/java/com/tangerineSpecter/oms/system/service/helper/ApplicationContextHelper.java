package com.tangerinespecter.oms.system.service.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring上下文帮助类
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;
    /**
     * bean存储Map
     */
    private static final Map<Class, Object> beanTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }

    /**
     * 根据bean的名字获取bean
     *
     * @param beanName bean的名字
     * @param <T>      泛型
     * @return 返回的bean
     */
    @SuppressWarnings("all")
    public static <T> T bean(String beanName) {
        if (applicationContext == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 根据类获取bean
     *
     * @param clazz 类
     * @param <T>   泛型
     * @return 返回的bean
     */
    @SuppressWarnings("all")
    public static <T> T bean(Class clazz) {
        if (applicationContext == null) {
            return null;
        }
        return (T) applicationContext.getBean(clazz);
    }

    /**
     * 根据bean的名字和类获取bean
     *
     * @param beanName bean名字
     * @param clazz    bean类
     * @param <T>      泛型
     * @return 返回bean
     */
    @SuppressWarnings("all")
    public static <T> T bean(String beanName, Class clazz) {
        if (applicationContext == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanName, clazz);
    }

    /**
     * bean的获取
     */
    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz) {
        T instance = (T) beanTableMap.get(clazz);
        if (instance != null) {
            return instance;
        }
        beanTableMap.put(clazz, bean(clazz));
        return (T) beanTableMap.get(clazz);
    }

    @Override
    public int getOrder() {
        //优先加载
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
}
