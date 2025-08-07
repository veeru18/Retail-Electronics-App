package com.ecommerce.retail_electronicsapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter @AllArgsConstructor
public class OTPExpiredException extends RuntimeException {

	private String message;
}
