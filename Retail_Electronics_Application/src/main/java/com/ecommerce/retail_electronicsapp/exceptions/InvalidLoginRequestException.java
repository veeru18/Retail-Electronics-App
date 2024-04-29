package com.ecommerce.retail_electronicsapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
@SuppressWarnings("serial")
public class InvalidLoginRequestException extends RuntimeException{

	private String message;
}
