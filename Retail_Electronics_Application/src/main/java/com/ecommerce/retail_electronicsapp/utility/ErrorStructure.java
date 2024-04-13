package com.ecommerce.retail_electronicsapp.utility;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ErrorStructure<T> {
	private int statuscode;
	private String message;
	private T rootCause;
	public ErrorStructure<T> setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}
	public ErrorStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public ErrorStructure<T> setRootCause(T rootCause) {
		this.rootCause = rootCause;
		return this;
	}
}
