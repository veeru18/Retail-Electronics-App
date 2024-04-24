package com.ecommerce.retail_electronicsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.retail_electronicsapp.entity.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {

	boolean existsByTokenAndIsBlockedTrue(String accessToken);
}
