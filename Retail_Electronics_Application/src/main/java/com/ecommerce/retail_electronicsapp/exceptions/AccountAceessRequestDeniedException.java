package com.ecommerce.retail_electronicsapp.exceptions;

import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter @SuppressWarnings("serial")
public class AccountAceessRequestDeniedException extends JwtException {

	public AccountAceessRequestDeniedException(String message) {
		super(message);
	}

}
