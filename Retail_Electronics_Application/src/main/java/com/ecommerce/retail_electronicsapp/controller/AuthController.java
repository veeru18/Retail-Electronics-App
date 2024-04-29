package com.ecommerce.retail_electronicsapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.retail_electronicsapp.requestdto.AuthRequest;
import com.ecommerce.retail_electronicsapp.requestdto.OTPRequest;
import com.ecommerce.retail_electronicsapp.requestdto.UserRequest;
import com.ecommerce.retail_electronicsapp.responsedto.AuthResponse;
import com.ecommerce.retail_electronicsapp.responsedto.UserResponse;
import com.ecommerce.retail_electronicsapp.service.AuthService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;
import com.ecommerce.retail_electronicsapp.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/re-v1")
//@CrossOrigin(origins ="http://localhost:5173/")
public class AuthController {

	private AuthService authService;
	//	private JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<SimpleResponseStructure> userRegistration(@RequestBody @Valid UserRequest userRequest) throws MessagingException {
		return authService.userRegistration(userRequest);
	}

	@PostMapping("/verify-email")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@RequestBody OTPRequest otpRequest){
		return authService.verifyOTP(otpRequest);
	}

	//	@GetMapping("/access-token")
	//	private String getAccessToken(@RequestParam String username, @RequestParam String role) {
	//		return jwtService.generateAccessToken(username,role);
	//	}
	//	
	//	@GetMapping("/refresh-token")
	//	private String getRefreshToken(@RequestParam String username, @RequestParam String role) {
	//		return jwtService.generateRefreshToken(username,role);
	//	}

	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> userRegistration(@RequestBody @Valid AuthRequest authRequest,
																@CookieValue(value="at",required=false) String accessToken,
																@CookieValue(value="rt",required=false) String refreshToken) {
		return authService.userLogin(authRequest,accessToken,refreshToken);
	}

	@PostMapping("/logout")
	public ResponseEntity<SimpleResponseStructure> userLogout(@CookieValue(value="at",required=false) String accessToken,
																@CookieValue(value="rt",required=false) String refreshToken) {
		return authService.userLogout(accessToken,refreshToken);
	}
	
	@GetMapping("/refresh-access")
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshAccessTokens( @CookieValue(value="at",required=false) String accessToken,
																			@CookieValue(value="rt",required=false) String refreshToken) {
		return authService.refreshAccessTokens(accessToken,refreshToken);
	}
}
