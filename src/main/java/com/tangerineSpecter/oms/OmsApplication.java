package com.tangerineSpecter.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OmsApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(OmsApplication.class);
		// app.setBannerMode(Mode.OFF);
		app.run(args);
	}

}
