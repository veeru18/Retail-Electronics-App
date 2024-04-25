package com.ecommerce.retail_electronicsapp.entity;

import java.util.Date;

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

@Entity @Getter 
@Setter
@Table(name = "accessTokens")
@AllArgsConstructor @NoArgsConstructor
public class AccessToken {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tokenId;
	@Column(length = 500)
	private String token;
	private Boolean isBlocked;
	private Date expiration;
	@ManyToOne
	private User user;
}
