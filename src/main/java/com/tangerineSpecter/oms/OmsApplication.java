package com.tangerinespecter.oms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableRabbit
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.tangerinespecter.oms.system.mapper")
public class OmsApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OmsApplication.class);
        // app.setBannerMode(Mode.OFF);
        app.run(args);
    }

}
