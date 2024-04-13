package com.ecommerce.retail_electronicsapp.responsedto;

import com.ecommerce.retail_electronicsapp.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserResponse {
	private int userId;
	private String displayName;
	private String username;
	private String email;
	private boolean isEmailVerified;
	private boolean isDeleted;
	private UserRole userRole;
}
