package com.ecommerce.retail_electronicsapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor @Getter
public class EmailInvalidException extends RuntimeException {

	private String message;
}
