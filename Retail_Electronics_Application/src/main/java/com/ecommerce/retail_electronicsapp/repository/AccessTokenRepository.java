package com.ecommerce.retail_electronicsapp.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.retail_electronicsapp.entity.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {

	boolean existsByTokenAndIsBlockedTrue(String accessToken);

	Optional<AccessToken> findByToken(String accessToken);

	List<AccessToken> findAllByExpirationLessThan(Date date);

}
