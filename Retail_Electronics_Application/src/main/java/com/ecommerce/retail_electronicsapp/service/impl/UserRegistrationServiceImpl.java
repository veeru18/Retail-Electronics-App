package com.ecommerce.retail_electronicsapp.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.retail_electronicsapp.entity.Customer;
import com.ecommerce.retail_electronicsapp.entity.Seller;
import com.ecommerce.retail_electronicsapp.entity.User;
import com.ecommerce.retail_electronicsapp.enums.UserRole;
import com.ecommerce.retail_electronicsapp.exceptions.EmailInvalidException;
import com.ecommerce.retail_electronicsapp.exceptions.IllegalAccessRequestExcpetion;
import com.ecommerce.retail_electronicsapp.repository.CustomerRepository;
import com.ecommerce.retail_electronicsapp.repository.SellerRepository;
import com.ecommerce.retail_electronicsapp.requestdto.UserRequest;
import com.ecommerce.retail_electronicsapp.responsedto.UserResponse;
import com.ecommerce.retail_electronicsapp.service.UserRegistrationService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

//	private UserRepository userRepository;
	private CustomerRepository customerRepo;
	private SellerRepository sellerRepo;
	private ResponseStructure<UserResponse> respStruct;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> userRegistration(UserRequest userRequest) {
		if(userRequest.getRole()==UserRole.CUSTOMER) {
			Customer uniqueCustomer=customerRepo.save(mapToCustomerEntity(userRequest, new Customer()));
			return ResponseEntity.ok(respStruct.setStatusCode(HttpStatus.OK.value())
					.setMessage("customer has been cached to save successfully")
					.setData(mapToUserResponse(uniqueCustomer)));
		}
		else if(userRequest.getRole()==UserRole.SELLER) {
			Seller uniqueSeller = sellerRepo.save(mapToSellerEntity(userRequest, new Seller()));
			return ResponseEntity.ok(respStruct.setStatusCode(HttpStatus.OK.value())
					.setMessage("seller has been cached to save successfully")
					.setData(mapToUserResponse(uniqueSeller)));
		}
		else {
			throw new IllegalAccessRequestExcpetion("invalid user role");
		}
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

	static User mapToUserEntity(UserRequest userRequest,User user) {
		String email = userRequest.getEmail();
		if(!email.endsWith("@gmail.com")) throw new EmailInvalidException("please re-enter valid gmail Id");
		user.setEmail(email).setDeleted(false).setEmailVerified(false).setDisplayName(userRequest.getName())
			.setUsername(email.substring(0,email.indexOf('@')))
			.setPassword(userRequest.getPassword()).setUserRole(userRequest.getRole());
		return user;
	}

	static Seller mapToSellerEntity(UserRequest userRequest, Seller seller) {
		String email = userRequest.getEmail();
		if(!email.endsWith("@gmail.com")) throw new EmailInvalidException("please re-enter valid gmail Id");
		seller.setEmail(email).setDeleted(false).setEmailVerified(false).setDisplayName(userRequest.getName())
				.setUsername(email.substring(0,email.indexOf('@')))
				.setPassword(userRequest.getPassword()).setUserRole(userRequest.getRole());
		return seller;
	}

	static Customer mapToCustomerEntity(UserRequest userRequest, Customer customer) {
		String email = userRequest.getEmail();
		if(!email.endsWith("@gmail.com")) throw new EmailInvalidException("please re-enter valid gmail Id");
		customer.setEmail(email).setDeleted(false).setEmailVerified(false).setDisplayName(userRequest.getName())
				.setUsername(email.substring(0,email.indexOf('@')))
				.setPassword(userRequest.getPassword()).setUserRole(userRequest.getRole());
		return customer;
	}
}
