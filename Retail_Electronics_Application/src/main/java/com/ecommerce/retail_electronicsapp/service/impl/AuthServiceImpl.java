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
import com.ecommerce.retail_electronicsapp.exceptions.OTPExpiredException;
import com.ecommerce.retail_electronicsapp.exceptions.RegistrationSessionExpiredException;
import com.ecommerce.retail_electronicsapp.mailservice.MailService;
import com.ecommerce.retail_electronicsapp.mailservice.MessageModel;
import com.ecommerce.retail_electronicsapp.repository.UserRepository;
import com.ecommerce.retail_electronicsapp.requestdto.OTPRequest;
import com.ecommerce.retail_electronicsapp.requestdto.UserRequest;
import com.ecommerce.retail_electronicsapp.responsedto.UserResponse;
import com.ecommerce.retail_electronicsapp.service.AuthService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;
import com.ecommerce.retail_electronicsapp.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;
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
	private SimpleResponseStructure simpleResponse;
	private MailService mailService;

	@Override
	public ResponseEntity<SimpleResponseStructure> userRegistration(UserRequest userRequest) throws MessagingException {
		if(userRepository.existsByEmail(userRequest.getEmail())) throw new EmailAlreadyExistsException("Email Already exists! please re-verify your account");
		User user=mapToUsersChildEntity(userRequest);
		String otp=generateOTP();
		otpCache.add(user.getEmail(), otp);
 		userCache.add(user.getEmail(), user);
 		System.out.println(otp);
		
		//should send otp to mail
 		sendOTP(user,otp);
 		return ResponseEntity.status(HttpStatus.ACCEPTED)
							.body(simpleResponse
									.setMessage("verify otp sent through mail to complete registration,OTP expires in 5 minutes")
									.setStatus(HttpStatus.ACCEPTED.value()));
	}

	private void sendOTP(User user, String otp) throws MessagingException {
		MessageModel model = MessageModel.builder().to(user.getEmail())
								.subject("Email Verification")
								.message(
											"<p>Hi, <br>"
											+ "Thank you for showing interest to shop in Retail Electronics,"
											+ " please Verify your Email ID : "+user.getEmail()+" using the OTP given below.</p>"
											+ "<br>"
											+ "<h1> OTP : "+otp+"</h1>"
											+ "<br><br>"
											+ "<p>Please Ignore if you haven't requested for verification</p>"
											+ "<br>"
											+ "<p>With best regards,</p>"
											+ "<h3>Retail Electronics</h3>"
											+ "<img src="+"'https://i.postimg.cc/h4HtBx5V/Electron-Logo.jpg'"+" alt='retail logo'/>"
										)
								.build();
		mailService.sendMessage(model);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OTPRequest otpRequest) {
		String email=otpRequest.getEmail(); //can't be null after validations

		if(otpCache.get(email)==null) throw new OTPExpiredException("OTP Has expired, please register again");
		if(!otpCache.get(email).equals(otpRequest.getOtp())) throw new OTPInvalidException("please enter valid OTP");
		
		User user=userCache.get(email);// will be either Customer (or) Seller
		if(user==null) throw new RegistrationSessionExpiredException("User , please register again");
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
