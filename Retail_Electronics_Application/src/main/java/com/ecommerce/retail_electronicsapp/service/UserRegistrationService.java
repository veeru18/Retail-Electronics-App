package com.ecommerce.retail_electronicsapp.service;

import org.springframework.http.ResponseEntity;

import com.ecommerce.retail_electronicsapp.requestdto.UserRequest;
import com.ecommerce.retail_electronicsapp.responsedto.UserResponse;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

public interface UserRegistrationService {
	
	ResponseEntity<ResponseStructure<UserResponse>> userRegistration(UserRequest userRequest);
}
