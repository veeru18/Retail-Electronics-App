package com.ecommerce.retail_electronicsapp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.retail_electronicsapp.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RetailUserDetailService implements UserDetailsService {
	
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByEmail(username)
				.map(user->new RetailUserDetails(user))
				.orElseThrow(()->new UsernameNotFoundException("Please enter a valid Email ID that you've registered with"));
	}

	
}
