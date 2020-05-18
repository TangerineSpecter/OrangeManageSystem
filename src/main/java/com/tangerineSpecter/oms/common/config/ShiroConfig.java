package com.tangerinespecter.oms.common.config;

import com.tangerinespecter.oms.common.security.CredentialMatcher;
import com.tangerinespecter.oms.common.security.MyShiroRealm;
import com.tangerinespecter.oms.common.security.UrlPermissionResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @author liangjun.zhou
 * @version v0.0.2
 * @date 2019年1月4日
 */
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager  DefaultFilter.class
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/errorPage");

        // 拦截器
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置shiro拦截器链
        //authc 需要认证
        //anon  不需要认证
        //user  验证通过或RememberMe登录的都可以
        // 定义filterChain，静态资源不拦截
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/vendor/**", "anon");

        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/errorPage", "anon");
        // druid数据源监控页面不拦截
        filterChainDefinitionMap.put("/druid/**", "anon");
        // 登出
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        // index必须用户认证通过才可以访问
        filterChainDefinitionMap.put("/index", "authc");
        // 除上以外所有url都必须所有用户认证才可以访问
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("Shiro拦截器加载成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm, DefaultWebSessionManager sessionManager) {
        // 配置SecurityManager，并注入shiroRealm
        log.info("===========shiro已加载===================");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 将自己的验证方式加入容器
     */
    @Bean("myShiroRealm")
    public MyShiroRealm myShiroRealm(@Qualifier("credentialMatcher") CredentialMatcher credentialMatcher) {
        // 配置Realm
        MyShiroRealm myShiroRealm = new MyShiroRealm();

        //使用缓存管理器
        myShiroRealm.setCacheManager(new MemoryConstrainedCacheManager());
        myShiroRealm.setCredentialsMatcher(credentialMatcher);
        myShiroRealm.setPermissionResolver(new UrlPermissionResolver());
        return myShiroRealm;
    }

    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher();
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        // Shiro生命周期处理器
        return new LifecycleBeanPostProcessor();
    }

    /**
     * cookie管理对象;
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * thymleaf里使用shiro
     */
//    @Bean(name = "shiroDialect")
//    public ShiroDialect shiroDialect() {
//        return new ShiroDialect();
//    }

    /**
     * 记住我Cookie
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        log.info("======ShiroConfiguration.rememberMeCookie()========================");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(30 * 60 * 60);
        return simpleCookie;
    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro代理配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAsynchronousChannelProvider() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(1800);
        return simpleCookie;
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager(@Qualifier("sessionIdCookie") SimpleCookie simpleCookie,
                                                   @Qualifier("sessionDAO") MemorySessionDAO sessionDAO) {  //配置默认的sesssion管理器
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //全局会话超时时间
        sessionManager.setGlobalSessionTimeout(2 * 60 * 60 * 1000);
        sessionManager.setSessionIdCookie(simpleCookie);
        sessionManager.setSessionDAO(sessionDAO());
        //删除无效的session
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检查过期的session
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler
        // 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        // 毫秒 十分钟
        sessionManager.setSessionValidationInterval(600000);
        //去掉URL中的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 注入会话管理
     *
     * @return
     */
    @Bean(name = "sessionDAO")
    public MemorySessionDAO sessionDAO() {
        return new MemorySessionDAO();
    }
}
