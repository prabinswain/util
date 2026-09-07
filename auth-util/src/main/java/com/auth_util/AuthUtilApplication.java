package com.auth_util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AuthUtilApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthUtilApplication.class, args);
	}

}
