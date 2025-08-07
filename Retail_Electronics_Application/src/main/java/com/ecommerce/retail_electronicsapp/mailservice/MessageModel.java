package com.ecommerce.retail_electronicsapp.mailservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MessageModel {

	private String to;
	private String subject;
	private String message;
	
}
