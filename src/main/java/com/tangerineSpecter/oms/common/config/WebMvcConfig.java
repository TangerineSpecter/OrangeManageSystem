package com.tangerinespecter.oms.common.config;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.net.URL;

/**
 * WebMvc配置类（自定义Thymeleaf模板）
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 设置上下文
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Thymeleaf模板资源解析器(自定义的需要做前缀绑定)
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.thymeleaf")
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setApplicationContext(this.applicationContext);
        //templates动态刷新
        URL resource = this.getClass().getClassLoader().getResource("templates/");
        //这里把系统获取到的Class的path替换为源码对应的Path，这样修改的时候就可以动态刷新
        String devResource = resource.getFile().replaceAll("target/classes", "src/main/resources");
        templateResolver.setPrefix("file:" + devResource);
        //不允许缓存
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    /**
     * Thymeleaf标准方言解释器
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        //支持spring EL表达式
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    /**
     * 视图解析器
     */
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        return thymeleafViewResolver;
    }

//    private ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setApplicationContext(applicationContext);
//        URL resource = this.getClass().getClassLoader().getResource("templates/");
//        //这里把系统获取到的Class的path替换为源码对应的Path，这样修改的时候就可以动态刷新
//        String devResource = resource.getFile().toString().replaceAll("target/classes", "src/main/resources");
//        resolver.setPrefix("file:" + devResource);
//        //不允许缓存
//        resolver.setCacheable(false);
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode(TemplateMode.HTML);
//        return resolver;
//    }
}
