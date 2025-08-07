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
		String usernames=username.split("@gmail.com")[0];
		return userRepo.findByUsername(usernames)
				.map(RetailUserDetails::new)
//				.map(user->new RetailUserDetails(user.getUsername(),user.getPassword(),user.getUserRole()) )
				.orElseThrow(()->new UsernameNotFoundException("Please enter a valid Email ID that you've registered with"));
	}

	
}
