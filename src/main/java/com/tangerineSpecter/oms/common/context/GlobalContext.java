package com.tangerinespecter.oms.common.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author 丢失的橘子
 * @date 2022年05月27日11:09:12
 */
@Component
public class GlobalContext implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext ctx;
    private static Environment env;

    /**
     * 本地线程
     */
    private static final ThreadLocal<UserContext> THREAD_LOCAL = new ThreadLocal<>();

    public static String getUid() {
        UserContext ctx = THREAD_LOCAL.get();
        if (ctx == null) {
            return null;
        } else {
            return ctx.getUid();
        }
    }

    public static void setUid(String uid) {
        UserContext ctx = THREAD_LOCAL.get();
        if (ctx == null) {
            ctx = new UserContext();
        }
        ctx.setUid(uid);
        THREAD_LOCAL.set(ctx);
    }

    public static String getNickName() {
        UserContext ctx = THREAD_LOCAL.get();
        if (ctx == null) {
            return null;
        } else {
            return ctx.getNickName();
        }
    }

    public static void setNickName(String nickName) {
        UserContext ctx = THREAD_LOCAL.get();
        if (ctx == null) {
            ctx = new UserContext();
        }
        ctx.setNickName(nickName);
        THREAD_LOCAL.set(ctx);
    }

    public static void removeThreadLocal() {
        THREAD_LOCAL.remove();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    public static ApplicationContext ofCtx() {
        return ctx;
    }

    public static Environment ofEnv() {
        return env;
    }

}
