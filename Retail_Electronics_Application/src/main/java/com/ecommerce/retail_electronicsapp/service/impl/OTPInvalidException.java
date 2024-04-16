package com.ecommerce.retail_electronicsapp.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter @AllArgsConstructor
public class OTPInvalidException extends RuntimeException {

	private String message;
}
