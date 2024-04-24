package com.ecommerce.retail_electronicsapp.responsedto;

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
	private String displayName;
	private Boolean authenticated;
	private long accessExpiration;
	private long refreshExpiration;
	
	public AuthResponse(int userId, String username, UserRole userRole, String displayName, boolean authenticated, long accessExpiration, long refreshExpiration) {
		super();
		this.userId = userId;
		this.username = username;
		this.userRole = userRole;
		this.displayName = displayName;
		this.authenticated=authenticated;
		this.accessExpiration = accessExpiration/1000;
		this.refreshExpiration = refreshExpiration/1000;
	}
	
}
