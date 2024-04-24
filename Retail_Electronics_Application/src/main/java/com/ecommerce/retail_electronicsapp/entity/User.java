package com.ecommerce.retail_electronicsapp.entity;

import com.ecommerce.retail_electronicsapp.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter
@Setter
@Table(name = "users")
@AllArgsConstructor @NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String displayName;
	@Column(unique = true)
	private String username;
	@Column(unique = true)
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	private boolean isEmailVerified;
	private boolean isDeleted;
	
	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	public User setUserRole(UserRole userRole) {
		this.userRole = userRole;
		return this;
	}
	public User setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
		return this;
	}
	public User setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}
	public User setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}
	
	
}
