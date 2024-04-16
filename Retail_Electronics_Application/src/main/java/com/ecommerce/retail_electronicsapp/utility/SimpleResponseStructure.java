package com.ecommerce.retail_electronicsapp.utility;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class SimpleResponseStructure {

	private int status;
	private String message;
	public SimpleResponseStructure setStatus(int status) {
		this.status = status;
		return this;
	}
	public SimpleResponseStructure setMessage(String message) {
		this.message = message;
		return this;
	}
	
}
