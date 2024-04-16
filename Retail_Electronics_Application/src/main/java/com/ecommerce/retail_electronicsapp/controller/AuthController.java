package com.ecommerce.retail_electronicsapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.retail_electronicsapp.requestdto.UserRequest;
import com.ecommerce.retail_electronicsapp.responsedto.UserResponse;
import com.ecommerce.retail_electronicsapp.service.AuthService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/re-v1")
public class AuthController {

	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<String> userRegistration(@RequestBody @Valid UserRequest userRequest) {
		return authService.userRegistration(userRequest);
	}
	
	@PostMapping("/verify-email")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@RequestParam(name="otp",required = false) String otp){
		return authService.verifyOTP(otp);
	}
}
