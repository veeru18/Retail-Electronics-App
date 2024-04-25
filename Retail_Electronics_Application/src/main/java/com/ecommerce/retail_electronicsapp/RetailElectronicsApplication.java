package com.ecommerce.retail_electronicsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RetailElectronicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailElectronicsApplication.class, args);
	}

}
