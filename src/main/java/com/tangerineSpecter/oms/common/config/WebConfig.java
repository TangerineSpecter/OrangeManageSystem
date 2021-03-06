package com.tangerinespecter.oms.common.config;

import com.tangerinespecter.oms.common.filter.AccessInterceptor;
import com.tangerinespecter.oms.common.interceptor.CustomHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * 拦截器配置
 *
 * @author TangerineSpecter
 * @version v0.0.1
 * @date 2019年1月4日F
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Resource
    private CustomHandlerInterceptor customHandlerInterceptor;
    @Resource
    private AccessInterceptor accessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customHandlerInterceptor).addPathPatterns("/**");
        registry.addInterceptor(accessInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }


}
