package com.ecommerce.retail_electronicsapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter @SuppressWarnings("serial")
public class JwtTokensMissingException extends RuntimeException {

	private String message;
}
