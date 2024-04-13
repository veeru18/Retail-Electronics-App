package com.ecommerce.retail_electronicsapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor @Getter
public class UsernameAlreadyExistsException extends RuntimeException {

	private String message;
}
