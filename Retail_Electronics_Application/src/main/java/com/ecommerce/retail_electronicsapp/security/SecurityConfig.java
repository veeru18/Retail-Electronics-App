package com.ecommerce.retail_electronicsapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.retail_electronicsapp.jwt.JwtFilter;
import com.ecommerce.retail_electronicsapp.jwt.JwtService;
import com.ecommerce.retail_electronicsapp.repository.AccessTokenRepository;
import com.ecommerce.retail_electronicsapp.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
// to enable method level security authorization check
// instead of conventional way of using auth.requestmatchers('our endpoint').hasAuthority('role')
@EnableMethodSecurity
public class SecurityConfig {
	
	private RetailUserDetailService userDetailService;
	private AccessTokenRepository accessTokenRepo;
	private RefreshTokenRepository refreshTokenRepo;
	private JwtService jwtService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth->auth
						//permitting only public endpoints that requires no authentication
						.requestMatchers("/api/re-v1/login","/api/re-v1/register","/api/re-v1/verify-email","/api/re-v1/products").permitAll()
						.anyRequest().authenticated())
				.csrf(csrf->csrf.disable())
				.sessionManagement(managementConfigure-> managementConfigure.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				//jwt authentication is stateless authentication which doesn't store any data in App server
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(new JwtFilter(accessTokenRepo, refreshTokenRepo, jwtService), UsernamePasswordAuthenticationFilter.class)
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
