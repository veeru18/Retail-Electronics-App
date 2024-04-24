package com.ecommerce.retail_electronicsapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.retail_electronicsapp.jwt.JwtFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	
	private JwtFilter jwtFilter;
	
	private RetailUserDetailService userDetailService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth->auth
						.requestMatchers("/**").permitAll()
						.anyRequest().authenticated())
				.csrf(csrf->csrf.disable())
				.sessionManagement(managementConfigure-> managementConfigure.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				//jwt authentication is stateless authentication which doesn't store any data in App server
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				// passing our jwt authentication filter as validating filter
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
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
		return authConfiguration.getAuthenticationManager();
	}
}
