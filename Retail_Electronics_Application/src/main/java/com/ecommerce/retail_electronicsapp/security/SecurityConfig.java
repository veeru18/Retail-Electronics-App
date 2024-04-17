package com.ecommerce.retail_electronicsapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	
	private RetailUserDetailService userDetailService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf->csrf.disable())
				//only one HttpMethod and String[] is allowed for this overloaded method
//				.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.GET,"/users/{userId}").hasAuthority("read"))
//				.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.PUT,"/users/{userId}").hasAuthority("write"))
//				.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.POST,"/users/register").hasAuthority("write"))
				.authorizeHttpRequests(auth->auth.requestMatchers
						("/api/re-v1","/api/re-v1/register","/api/re-v1/verify-email")
						.permitAll().anyRequest().authenticated())
				.formLogin(Customizer.withDefaults())
				.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);	//hashes password 12 times which is widely used
	}

	//bean methods must always be default
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailService);
		return authenticationProvider;
	}
}
