package com.tangerinespecter.oms;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import com.tangerinespecter.oms.common.AppRunWrapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableRabbit
@EnableSwaggerBootstrapUi
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.tangerinespecter.oms.system.mapper")
public class OmsApplication {
	
	public static void main(String[] args) {
		AppRunWrapper app = new AppRunWrapper();
		// app.setBannerMode(Mode.OFF);
		app.run(OmsApplication.class, args);
	}
	
}
