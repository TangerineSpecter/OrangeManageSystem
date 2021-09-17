package com.tangerinespecter.oms.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration
@EnableSwagger2//启用swagger
@EnableSwaggerBootstrapUi//启动新版ui
public class SwaggerConfig {

	@Bean
	public Docket customDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.build();
	}

	/**
	 * 配置Api相关信息
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact("丢失的橘子", "https://github.com/TangerineSpecter", "993033472@qq.com");
		return new ApiInfoBuilder()
				.title("橘子管理后台文档")
				.description("橘子管理后台接口文档")
				.contact(contact)
				.version("1.0.0")
				.build();
	}
}