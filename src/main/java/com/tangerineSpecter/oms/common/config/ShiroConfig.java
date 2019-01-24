package com.tangerineSpecter.oms.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import com.tangerineSpecter.oms.common.security.MyShiroRealm;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author liangjun.zhou
 * @Datetime 2019年1月4日
 * @version v0.0.2
 *
 */
@Slf4j
@Configuration
public class ShiroConfig {

	// Filter工厂，设置对应的过滤条件和跳转条件
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置securityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 登录的url
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后跳转的url
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 错误页面，认证不通过跳转
		shiroFilterFactoryBean.setUnauthorizedUrl("/errorPage");

		// 拦截器
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 定义filterChain，静态资源不拦截
		// filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/vendor/**", "anon");

		filterChainDefinitionMap.put("/register", "anon");
		filterChainDefinitionMap.put("/userLogin", "anon");
		// druid数据源监控页面不拦截
		filterChainDefinitionMap.put("/druid/**", "anon");
		// 登出
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/", "anon");
		// 除上以外所有url都必须所有用户认证通过才可以访问
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		log.info("Shiro拦截器加载成功");
		return shiroFilterFactoryBean;
	}

	// 将自己的验证方式加入容器
	@Bean
	public MyShiroRealm myShiroRealm() {
		// 配置Realm
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}

	// 权限管理，配置主要是Realm的管理认证
	@Bean
	public SecurityManager securityManager() {
		// 配置SecurityManager，并注入shiroRealm
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		// Shiro生命周期处理器
		return new LifecycleBeanPostProcessor();
	}

	// 加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
