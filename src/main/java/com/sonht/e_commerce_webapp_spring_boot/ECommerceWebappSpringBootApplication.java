package com.sonht.e_commerce_webapp_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)

public class ECommerceWebappSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceWebappSpringBootApplication.class, args);
	}

}
