package com.ecommerce.retail_electronicsapp.responsedto;

import org.springframework.beans.factory.annotation.Value;

import com.ecommerce.retail_electronicsapp.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder @NoArgsConstructor
public class AuthResponse {

	private int userId;
	private String username;
	private UserRole userRole;
	private long accessExpiration;
	private long refreshExpiration;
	
	public AuthResponse(int userId, String username, UserRole userRole,@Value("${}") long accessExpiration, long refreshExpiration) {
		super();
		this.userId = userId;
		this.username = username;
		this.userRole = userRole;
		this.accessExpiration = accessExpiration/1000;
		this.refreshExpiration = refreshExpiration/1000;
	}
	
}
