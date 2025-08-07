package com.ecommerce.retail_electronicsapp.service;

import org.springframework.http.ResponseEntity;

import com.ecommerce.retail_electronicsapp.requestdto.AuthRequest;
import com.ecommerce.retail_electronicsapp.requestdto.OTPRequest;
import com.ecommerce.retail_electronicsapp.requestdto.UserRequest;
import com.ecommerce.retail_electronicsapp.responsedto.AuthResponse;
import com.ecommerce.retail_electronicsapp.responsedto.UserResponse;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;
import com.ecommerce.retail_electronicsapp.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;

public interface AuthService {
	
	ResponseEntity<SimpleResponseStructure> userRegistration(UserRequest userRequest) throws MessagingException;

	ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OTPRequest otpRequest);

	ResponseEntity<ResponseStructure<AuthResponse>> userLogin(AuthRequest authRequest, String accessToken, String refreshToken);

	ResponseEntity<SimpleResponseStructure> userLogout(String accessToken, String refreshToken);

	ResponseEntity<ResponseStructure<AuthResponse>> refreshAccessTokens(String accessToken, String refreshToken);

}
