package com.ecommerce.retail_electronicsapp.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecommerce.retail_electronicsapp.exceptions.AccountAceessRequestDeniedException;
import com.ecommerce.retail_electronicsapp.exceptions.AddressNotFoundException;
import com.ecommerce.retail_electronicsapp.exceptions.ContactNotFoundException;
import com.ecommerce.retail_electronicsapp.exceptions.ContactsPerAddressLimitExceededException;
import com.ecommerce.retail_electronicsapp.exceptions.CustomerAddressLimitExceededException;
import com.ecommerce.retail_electronicsapp.exceptions.CustomerAddressNotFoundException;
import com.ecommerce.retail_electronicsapp.exceptions.EmailAlreadyExistsException;
import com.ecommerce.retail_electronicsapp.exceptions.EmailInvalidException;
import com.ecommerce.retail_electronicsapp.exceptions.IllegalAccessRequestExcpetion;
import com.ecommerce.retail_electronicsapp.exceptions.InvalidLoginRequestException;
import com.ecommerce.retail_electronicsapp.exceptions.JwtTokensMissingException;
import com.ecommerce.retail_electronicsapp.exceptions.OTPExpiredException;
import com.ecommerce.retail_electronicsapp.exceptions.OTPInvalidException;
import com.ecommerce.retail_electronicsapp.exceptions.RegistrationSessionExpiredException;
import com.ecommerce.retail_electronicsapp.exceptions.SellerAddressLimitExceededException;
import com.ecommerce.retail_electronicsapp.exceptions.SellerAddressNotFoundException;
import com.ecommerce.retail_electronicsapp.exceptions.UsernameAlreadyExistsException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	private ErrorStructure<String> error;	

	private ErrorStructure<Map<String,String>> struct;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		//		List<ObjectError> allErrors = ex.getAllErrors();
		Map<String,String> messages=new HashMap<>();
		ex.getAllErrors().forEach(error->messages.put(((FieldError)error).getField(),error.getDefaultMessage()));
		//		{
		//			FieldError fieldError=(FieldError)err;
		//			errors.put(((FieldError)err).getField(),err.getDefaultMessage());
		//		});
		return ResponseEntity.badRequest().body(
				struct.setRootCause(messages)
				.setMessage("Invalid Data Entered")
				.setStatuscode(HttpStatus.BAD_REQUEST.value()));
	}

	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status,String message,String rootCause){
		return new ResponseEntity<ErrorStructure<String>>(error.setStatuscode(status.value())
				.setRootCause(rootCause)
				.setMessage(message),status);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAccountAceessRequestDeniedException (AccountAceessRequestDeniedException accessex){
		return errorResponse(HttpStatus.BAD_REQUEST,accessex.getMessage(),"login again for access");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAddressNotFoundException(AddressNotFoundException addrex){
		return errorResponse(HttpStatus.NOT_FOUND,addrex.getMessage(),"please enter a valid address Id");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleContactNotFoundException(ContactNotFoundException contactex){
		return errorResponse(HttpStatus.NOT_FOUND,contactex.getMessage(),"please enter a valid contact Id");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleContactsPerAddressLimitExceededException(ContactsPerAddressLimitExceededException limitex){
		return errorResponse(HttpStatus.BAD_REQUEST,limitex.getMessage(),"address has reached it's limit to add any new contacts");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleCustomerAddressLimitExceededException(CustomerAddressLimitExceededException custaddrex){
		return errorResponse(HttpStatus.BAD_REQUEST,custaddrex.getMessage(),"customer addresses has reached it's limit of 5, can't add any new addresses");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleSellerAddressLimitExceededException(SellerAddressLimitExceededException selleraddrex){
		return errorResponse(HttpStatus.BAD_REQUEST,selleraddrex.getMessage(),"address has reached it's limit of 1, can't add any new addresses");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleCustomerAddressNotFoundException(CustomerAddressNotFoundException custaddrex){
		return errorResponse(HttpStatus.NOT_FOUND,custaddrex.getMessage(),"customer address was not found");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleSellerAddressNotFoundException(SellerAddressNotFoundException selleraddrex){
		return errorResponse(HttpStatus.NOT_FOUND,selleraddrex.getMessage(),"seller address was not found");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException emailex){
		return errorResponse(HttpStatus.FOUND,emailex.getMessage(),"User Email Already exists! please enter a Valid Email ID");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleEmailInvalidException(EmailInvalidException eminex){
		return errorResponse(HttpStatus.BAD_REQUEST,eminex.getMessage(),"User Email Already exists! please enter a Valid Email ID");
	}	

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleIllegalAccessRequestExcpetion(IllegalAccessRequestExcpetion accessexception){
		return errorResponse(HttpStatus.UNAUTHORIZED,accessexception.getMessage(),"specify Role! please enter a Valid role");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInvalidLoginRequestException(InvalidLoginRequestException accessexception){
		return errorResponse(HttpStatus.UNAUTHORIZED,accessexception.getMessage(),"improper login has been requested");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleJwtTokensMissingException(JwtTokensMissingException loginex){
		return errorResponse(HttpStatus.NOT_FOUND,loginex.getMessage(),"access or refresh or both tokens are missing");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleOTPExpiredException(OTPExpiredException otpSessionex){
		return errorResponse(HttpStatus.BAD_REQUEST,otpSessionex.getMessage(),"your OTP verification session expired, please try to get new otp again");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleOTPInvalidException(OTPInvalidException otpex){
		return errorResponse(HttpStatus.BAD_REQUEST,otpex.getMessage(),"user email persisted! please enter a Valid OTP that's sent to your email");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleRegistrationSessionExpiredException(RegistrationSessionExpiredException regSessionex){
		return errorResponse(HttpStatus.BAD_REQUEST,regSessionex.getMessage(),"please register again, your session expired");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException nameex){
		return errorResponse(HttpStatus.FOUND,nameex.getMessage(),"User Name Already exists! please enter a Valid Username");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUsernameNotFoundException(UsernameNotFoundException securityloginuserex){
		return errorResponse(HttpStatus.NOT_FOUND,securityloginuserex.getMessage(),"your OTP verification session expired, please try to get new otp again");
	}
}
