package com.ecommerce.retail_electronicsapp.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPRequest {

	@NotNull(message="otp cannot be empty,check your email") @NotBlank(message="otp cannot be empty,check your email")
	private String otp;
	@NotNull(message="email cannot be empty") @NotBlank(message="otp cannot be empty,check your email")
	private String email;
}
