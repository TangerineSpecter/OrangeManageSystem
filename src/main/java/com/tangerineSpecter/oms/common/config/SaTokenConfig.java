package com.tangerinespecter.oms.common.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.result.ServiceResult;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 权限验证配置类
 *
 * @author TangerineSpecter
 * @date 2021年08月18日01:02:28
 */
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册注解拦截器
        registry.addInterceptor(new SaAnnotationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("");
    }

    /**
     * 注册 Sa-Token 全局过滤器
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                //指定 拦截路由 和 放行路由
                .addInclude("/**")
                //认证函数：每次请求执行
                .setAuth(r -> {
                    System.out.println("sa全局认证...");
                })
                //异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    System.out.println("sa全局异常...");
                    return ServiceResult.error(RetCode.PARAM_ERROR);
                })
                //前置函数：在每次认证函数之前执行
                .setBeforeAuth(r -> {
                    //设置一些安全响应头
                    SaHolder.getResponse()
                            //服务器名称
                            .setServer("oms-server")
                            //是否可以在iframe显示视图：DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                            .setHeader("X-Frame-Options", "SAMEORIGIN")
                            //是否启用浏览器默认XSS防护：0=禁用 | 1=启用 | 1；mode=block 启用，并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-XSS-Protection", "1；mode=block")
                            //禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff");
                });
    }
}
