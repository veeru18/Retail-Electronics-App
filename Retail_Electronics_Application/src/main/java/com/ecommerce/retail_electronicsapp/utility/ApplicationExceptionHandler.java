package com.ecommerce.retail_electronicsapp.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecommerce.retail_electronicsapp.exceptions.EmailAlreadyExistsException;
import com.ecommerce.retail_electronicsapp.exceptions.EmailInvalidException;
import com.ecommerce.retail_electronicsapp.exceptions.IllegalAccessRequestExcpetion;
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
	public ResponseEntity<ErrorStructure<String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException emailex){
		return errorResponse(HttpStatus.BAD_REQUEST,emailex.getMessage(),"User Email Already exists! please enter a Valid Email ID");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleEmailInvalidException(EmailInvalidException eminex){
		return errorResponse(HttpStatus.BAD_REQUEST,eminex.getMessage(),"User Email Already exists! please enter a Valid Email ID");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException nameex){
		return errorResponse(HttpStatus.BAD_REQUEST,nameex.getMessage(),"User Name Already exists! please enter a Valid Username");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleIllegalAccessRequestExcpetion(IllegalAccessRequestExcpetion accessexception){
		return errorResponse(HttpStatus.UNAUTHORIZED,accessexception.getMessage(),"Specify Role! please enter a Valid role");
	}
}
