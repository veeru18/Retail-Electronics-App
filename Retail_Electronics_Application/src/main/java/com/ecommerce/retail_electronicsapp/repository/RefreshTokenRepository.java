package com.ecommerce.retail_electronicsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.retail_electronicsapp.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	boolean existsByTokenAndIsBlockedTrue(String refreshToken);

}
