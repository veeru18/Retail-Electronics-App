package com.ecommerce.retail_electronicsapp.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.retail_electronicsapp.entity.User;

@Configuration
public class CacheBeanConfig {

	@Bean
	CacheStore<String> otpCache(){
		return new CacheStore<>(5);
	}
	
	@Bean
	CacheStore<User> userCache(){
		return new CacheStore<>(15);
	}
}
