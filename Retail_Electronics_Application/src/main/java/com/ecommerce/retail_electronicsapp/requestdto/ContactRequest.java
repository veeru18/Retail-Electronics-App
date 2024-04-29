package com.ecommerce.retail_electronicsapp.requestdto;

import com.ecommerce.retail_electronicsapp.enums.Priority;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class ContactRequest {

	@Pattern(regexp = "^[a-zA-Z]+$",message = "should contain only alphabet")
	private String name;
//	@Pattern(regexp="^[0-9]+$",message="please enter only numbers")
	private long phoneNumber;
	@Email(regexp = "^[a-zA-Z0-9]+[a-zA-Z0-9._%+-]*@gmail\\.com$",message = "Please enter valid gmail with @ and .")
	@NotBlank @NotNull
	private String email;
	@NotNull(message = "address type is required")
	private Priority priority;
}
