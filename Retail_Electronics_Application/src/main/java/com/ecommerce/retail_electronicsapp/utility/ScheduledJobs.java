package com.ecommerce.retail_electronicsapp.utility;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ecommerce.retail_electronicsapp.repository.AccessTokenRepository;
import com.ecommerce.retail_electronicsapp.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduledJobs {

	private AccessTokenRepository accessTokenRepository;
	private RefreshTokenRepository refreshTokenRepository;
	
	@Scheduled(fixedDelay = 600000l)
	public void deleteTokensIfExpired() {
		
		accessTokenRepository.deleteAll(accessTokenRepository.findAllByExpirationLessThan(new Date()));
		refreshTokenRepository.deleteAll(refreshTokenRepository.findAllByExpirationLessThan(new Date()));
		
	}
}
