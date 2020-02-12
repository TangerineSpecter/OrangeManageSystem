package com.tangerinespecter.oms.common.config;

import com.tangerinespecter.oms.common.security.CredentialMatcher;
import com.tangerinespecter.oms.common.security.MyShiroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.nio.ch.DefaultAsynchronousChannelProvider;

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
        // 错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/404");

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
        // druid数据源监控页面不拦截
        filterChainDefinitionMap.put("/druid/**", "anon");
        // 登出
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        // index必须用户认证通过才可以访问
        filterChainDefinitionMap.put("/index", "authc");
        // 除上以外所有url都必须所有用户登录才可以访问
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("Shiro拦截器加载成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm) {
        // 配置SecurityManager，并注入shiroRealm
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
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
        myShiroRealm.setCacheManager(new MemoryConstrainedCacheManager());
        myShiroRealm.setCredentialsMatcher(credentialMatcher);
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
        simpleCookie.setMaxAge(259200);
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
}
