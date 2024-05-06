package com.ecommerce.retail_electronicsapp.service.impl;

import java.time.Duration;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.retail_electronicsapp.cache.CacheStore;
import com.ecommerce.retail_electronicsapp.entity.AccessToken;
import com.ecommerce.retail_electronicsapp.entity.Customer;
import com.ecommerce.retail_electronicsapp.entity.RefreshToken;
import com.ecommerce.retail_electronicsapp.entity.Seller;
import com.ecommerce.retail_electronicsapp.entity.User;
import com.ecommerce.retail_electronicsapp.enums.UserRole;
import com.ecommerce.retail_electronicsapp.exceptions.AccountAceessRequestDeniedException;
import com.ecommerce.retail_electronicsapp.exceptions.EmailAlreadyExistsException;
import com.ecommerce.retail_electronicsapp.exceptions.IllegalAccessRequestExcpetion;
import com.ecommerce.retail_electronicsapp.exceptions.InvalidLoginRequestException;
import com.ecommerce.retail_electronicsapp.exceptions.JwtTokensMissingException;
import com.ecommerce.retail_electronicsapp.exceptions.OTPExpiredException;
import com.ecommerce.retail_electronicsapp.exceptions.OTPInvalidException;
import com.ecommerce.retail_electronicsapp.exceptions.RegistrationSessionExpiredException;
import com.ecommerce.retail_electronicsapp.jwt.JwtService;
import com.ecommerce.retail_electronicsapp.mailservice.MailService;
import com.ecommerce.retail_electronicsapp.mailservice.MessageModel;
import com.ecommerce.retail_electronicsapp.repository.AccessTokenRepository;
import com.ecommerce.retail_electronicsapp.repository.RefreshTokenRepository;
import com.ecommerce.retail_electronicsapp.repository.UserRepository;
import com.ecommerce.retail_electronicsapp.requestdto.AuthRequest;
import com.ecommerce.retail_electronicsapp.requestdto.OTPRequest;
import com.ecommerce.retail_electronicsapp.requestdto.UserRequest;
import com.ecommerce.retail_electronicsapp.responsedto.AuthResponse;
import com.ecommerce.retail_electronicsapp.responsedto.UserResponse;
import com.ecommerce.retail_electronicsapp.service.AuthService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;
import com.ecommerce.retail_electronicsapp.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService {

	private UserRepository userRepository;
	private JwtService jwtService;
	//	private CustomerRepository customerRepo;
	//	private SellerRepository sellerRepo;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> respStruct;
	private ResponseStructure<AuthResponse> authResponseStructure;
	private SimpleResponseStructure simpleResponse;
	private MailService mailService;
	private PasswordEncoder passwordEncoder;
	private AccessTokenRepository accessRepository;
	private RefreshTokenRepository refreshRepository;
	private AuthenticationManager authManager;

	public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, CacheStore<String> otpCache,
			CacheStore<User> userCache, ResponseStructure<UserResponse> respStruct,
			SimpleResponseStructure simpleResponse, MailService mailService, PasswordEncoder passwordEncoder
			,ResponseStructure<AuthResponse> authResponseStructure,RefreshTokenRepository refreshRepository
			,AccessTokenRepository accessRepository,AuthenticationManager authManager) {

		this.userRepository = userRepository;
		this.accessRepository = accessRepository;
		this.refreshRepository = refreshRepository;
		this.jwtService = jwtService;
		this.otpCache = otpCache;
		this.userCache = userCache;
		this.respStruct = respStruct;
		this.simpleResponse = simpleResponse;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
		this.authResponseStructure = authResponseStructure;
		this.authManager = authManager;

	} 

	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiration;

	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiration;

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
				.subject("Email Verification for Retail Electronics site")
				.message(
						"<p>Hi "+user.getDisplayName()+", <br>"
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
		user=userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(respStruct.setStatusCode(HttpStatus.CREATED.value())
				.setMessage("OTP verified succesfully")
				.setData(mapToUserResponse(user)));
	}

	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000,999999));
	}

	UserResponse mapToUserResponse(User user) {
		UserResponse response = UserResponse.builder()
				.displayName(user.getDisplayName()).userId(user.getUserId())
				.username(user.getUsername()).email(user.getEmail())
				.userRole(user.getUserRole()).isDeleted(user.isDeleted())
				.isEmailVerified(user.isEmailVerified())
				.build();
		return response;
	}

	User mapToUsersChildEntity(UserRequest userRequest) {
		UserRole role=userRequest.getUserRole();
		User user=null;

		switch(role) {
		case CUSTOMER -> user=new Customer();
		case SELLER -> user=new Seller();
		default -> throw new IllegalAccessRequestExcpetion("Invalid Role");
		}

		user.setDisplayName(userRequest.getName())
		.setDeleted(false).setEmailVerified(false)
		.setEmail(userRequest.getEmail()).setPassword(passwordEncoder.encode(userRequest.getPassword()))
		.setUserRole(role).setUsername(userRequest.getEmail().split("@gmail.com")[0]);
		return user;
	}

	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> userLogin(AuthRequest authRequest, String accessToken, String refreshToken) {
//		if((accessToken==null && refreshToken==null) || (accessToken!=null && refreshToken==null)) {}
			//continue
		if(accessToken==null && refreshToken!=null) throw new InvalidLoginRequestException("user already logged in, send a refresh request");
		String username=authRequest.getUsername().split("@gmail.com")[0];

		Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		if(!authentication.isAuthenticated()) throw new BadCredentialsException("exception cause of bad credentials"); 
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		System.out.println(username);

		return userRepository.findByUsername(username).map(user->{

			HttpHeaders headers=new HttpHeaders();
			generateRefreshToken(user, headers);
			generateAccessToken(user, headers);

			//			System.out.println(headers.toString());
			return ResponseEntity.ok().headers(headers).body(authResponseStructure
					.setStatusCode(HttpStatus.ACCEPTED.value())
					.setData(mapToAuthResponse(user, refreshExpiration, accessExpiration))
					.setMessage("Login success"));
		}).orElseThrow(()->new RuntimeException("invalid creds"));
	}

	private void generateRefreshToken(User user,HttpHeaders header) {
		String refreshToken = jwtService.generateRefreshToken(user.getUsername(),user.getUserRole().name());
		header.add(HttpHeaders.SET_COOKIE, configureCookie("rt",refreshToken,refreshExpiration));

		RefreshToken token=new RefreshToken();
		token.setUser(user);
		token.setToken(refreshToken);
		token.setIsBlocked(false);
		Date date=new Date();
		date.setTime(date.getTime()+accessExpiration);
		token.setExpiration(date);
		refreshRepository.save(token);		
	}

	private void generateAccessToken(User user,HttpHeaders header) {
		String accessToken = jwtService.generateAccessToken(user.getUsername(),user.getUserRole().name());
		header.add(HttpHeaders.SET_COOKIE, configureCookie("at",accessToken,accessExpiration));
		AccessToken token=new AccessToken();
		token.setUser(user);
		token.setToken(accessToken);
		token.setIsBlocked(false);
		Date date=new Date();
		date.setTime(date.getTime()+accessExpiration);
		token.setExpiration(date);
		accessRepository.save(token);
	}

	private String configureCookie(String string, String tokenValue, long expiration) {
		return ResponseCookie.from(string, tokenValue)
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.sameSite("Lax")
				.maxAge(Duration.ofMillis(expiration))
				.build().toString();
	}

	AuthResponse mapToAuthResponse(User user,long refreshExpiry,long accessExpiry) {
		return AuthResponse.builder().userId(user.getUserId())
				.username(user.getUsername())
				.userRole(user.getUserRole())
				.authenticated(user.isEmailVerified())
				.displayName(user.getDisplayName())
				.accessExpiration(accessExpiry)
				.refreshExpiration(refreshExpiry)
				.build();
	}

	@Override
	public ResponseEntity<SimpleResponseStructure> userLogout(String accessToken, String refreshToken) {
		if(accessToken==null || refreshToken==null) throw new JwtTokensMissingException("user not logged in,access or refresh tokens missing");
		AccessToken at=accessRepository.findByToken(accessToken).get();
		RefreshToken rt=refreshRepository.findByToken(refreshToken).get();

		HttpHeaders headers=new HttpHeaders();
		invalidateCookies(headers,refreshToken,accessToken);

		at.setIsBlocked(true);
		accessRepository.save(at);
		rt.setIsBlocked(true);
		refreshRepository.save(rt);

		return ResponseEntity.ok().headers(headers).body(simpleResponse
				.setStatus(HttpStatus.OK.value())
				.setMessage("User logged out successfully"));
	}

	private void invalidateCookies(HttpHeaders headers, String refreshToken, String accessToken) {
		headers.add(HttpHeaders.SET_COOKIE, deConfigureCookie("rt"));
		headers.add(HttpHeaders.SET_COOKIE, deConfigureCookie("at"));
	}

	private String deConfigureCookie(String string) {
		return ResponseCookie.from(string, "")
				.maxAge(0)
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.sameSite("Lax")
				.build().toString();
	}

	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshAccessTokens(String accessToken,
			String refreshToken) {
		accessRepository.findByToken(accessToken).ifPresent(token->{
			token.setIsBlocked(true);
			System.out.println("access token blocked");
			accessRepository.save(token);
		});
		if(!refreshRepository.existsByTokenAndIsBlockedTrue(refreshToken)) throw new AccountAceessRequestDeniedException("account has been logged out, please login again");
		//validating the token issued date with current date
		HttpHeaders headers=new HttpHeaders();
		return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).map(user ->{
			if(jwtService.getIssuedAt(refreshToken).before(new Date())) {
				System.out.println("genrating new refresh token");
				generateRefreshToken(user, headers);
			}
			else {
				System.out.println("using the same refresh token");
				headers.add(HttpHeaders.SET_COOKIE, refreshToken);
			}
			
			generateAccessToken(user, headers);
			
			return ResponseEntity.ok().headers(headers).body(authResponseStructure.setMessage("Tokens are refreshed")
																	.setStatusCode(HttpStatus.OK.value())
																	.setData(mapToAuthResponse(user, refreshExpiration, accessExpiration)));
		}).orElseThrow(()-> new UsernameNotFoundException("user hasn't logged in, please sign in again"));
		
	}

}
