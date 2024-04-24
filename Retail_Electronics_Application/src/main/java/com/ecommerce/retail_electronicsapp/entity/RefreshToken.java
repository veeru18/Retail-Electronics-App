package com.ecommerce.retail_electronicsapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Table(name = "refreshTokens")
@AllArgsConstructor @NoArgsConstructor
@Setter
public class RefreshToken {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tokenId;
	@Column(length = 500)
	private String token;
	private Boolean isBlocked;
	private long expiration;
	@ManyToOne
	private User user;
}
