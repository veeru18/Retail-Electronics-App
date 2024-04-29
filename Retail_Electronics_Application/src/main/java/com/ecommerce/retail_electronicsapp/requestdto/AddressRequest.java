package com.ecommerce.retail_electronicsapp.requestdto;

import com.ecommerce.retail_electronicsapp.enums.AddressType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

	@Pattern(regexp = "^[a-zA-Z0-9,.-@#$& ]+$",message = "can contain alphanumeric")
	private String streetAddress;
	@Pattern(regexp = "^[a-zA-Z0-9,.-@#$& ]+$",message = "can contain alphanumeric")
	private String streetAddressAdditional;
	@NotNull(message = "address type is required")
	private AddressType addressType;
	@Pattern(regexp = "^[a-zA-Z]+$",message = "should contain only alphabet")
	private String city;
	@Pattern(regexp = "^[a-zA-Z]+$",message = "should contain only alphabet")
	private String state;
//	@Pattern(regexp="^[0-9]+$",message="please enter only numbers")
	private int pincode;
	
}
