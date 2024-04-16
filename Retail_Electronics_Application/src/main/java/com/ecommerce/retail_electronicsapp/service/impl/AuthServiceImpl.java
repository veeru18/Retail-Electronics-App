package com.ecommerce.retail_electronicsapp.service.impl;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.retail_electronicsapp.cache.CacheStore;
import com.ecommerce.retail_electronicsapp.entity.Customer;
import com.ecommerce.retail_electronicsapp.entity.Seller;
import com.ecommerce.retail_electronicsapp.entity.User;
import com.ecommerce.retail_electronicsapp.enums.UserRole;
import com.ecommerce.retail_electronicsapp.exceptions.EmailAlreadyExistsException;
import com.ecommerce.retail_electronicsapp.exceptions.IllegalAccessRequestExcpetion;
//import com.ecommerce.retail_electronicsapp.repository.CustomerRepository;
//import com.ecommerce.retail_electronicsapp.repository.SellerRepository;
import com.ecommerce.retail_electronicsapp.repository.UserRepository;
import com.ecommerce.retail_electronicsapp.requestdto.UserRequest;
import com.ecommerce.retail_electronicsapp.responsedto.UserResponse;
import com.ecommerce.retail_electronicsapp.service.AuthService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private UserRepository userRepository;
//	private CustomerRepository customerRepo;
//	private SellerRepository sellerRepo;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> respStruct;

	@Override
	public ResponseEntity<String> userRegistration(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail())) throw new EmailAlreadyExistsException("Email Already exists! please re-verify your account");
		User user=mapToUsersChildEntity(userRequest);
		String otp=generateOTP();
		if(otpCache.get("otp")!=null)
		otpCache.add("otp", otp);
		if(otpCache.get("user")!=null)
		userCache.add("user", user);
		
		return ResponseEntity.ok(otp);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(String otp) {
		if(!otpCache.get("otp").equals(otp)) throw new OTPInvalidException("please enter valid OTP");
		User user=userCache.get("user");// will be either Customer (or) Seller
		user.setEmailVerified(true);
//		user=userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(respStruct.setStatusCode(HttpStatus.CREATED.value())
																.setMessage("OTP verified succesfully")
																.setData(mapToUserResponse(user)));
	}
	
	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000,999999));
	}

	static UserResponse mapToUserResponse(User user) {
		UserResponse response = UserResponse.builder()
								.displayName(user.getDisplayName()).userId(user.getUserId())
								.username(user.getUsername()).email(user.getEmail())
								.userRole(user.getUserRole()).isDeleted(user.isDeleted())
								.isEmailVerified(user.isEmailVerified())
								.build();
		return response;
	}

	static User mapToUsersChildEntity(UserRequest userRequest) {
		UserRole role=userRequest.getRole();
		User user=null;
		
		switch(role) {
			case CUSTOMER -> user=new Customer();
			case SELLER -> user=new Seller();
			default -> throw new IllegalAccessRequestExcpetion("Invalid Role");
		}
		
		user.setDisplayName(userRequest.getName())
			.setDeleted(false).setEmailVerified(false)
			.setEmail(userRequest.getEmail()).setPassword(userRequest.getPassword())
			.setUserRole(role).setUsername(userRequest.getEmail().split("@gmail.com")[0]);
		return user;
	}

}
