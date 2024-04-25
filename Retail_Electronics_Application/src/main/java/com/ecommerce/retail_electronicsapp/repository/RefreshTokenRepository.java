package com.ecommerce.retail_electronicsapp.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.retail_electronicsapp.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	boolean existsByTokenAndIsBlockedTrue(String refreshToken);

	Optional<RefreshToken> findByToken(String refreshToken);

	List<RefreshToken> findAllByExpirationLessThan(Date date);

}
