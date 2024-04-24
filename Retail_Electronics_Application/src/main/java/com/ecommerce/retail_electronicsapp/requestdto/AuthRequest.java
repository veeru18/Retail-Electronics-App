package com.ecommerce.retail_electronicsapp.requestdto;

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
public class AuthRequest {

	@Email(regexp = "^[a-zA-Z0-9]+[a-zA-Z0-9._%+-]*@gmail\\.com$",message = "Please enter valid gmail with @ and .")
	@NotBlank @NotNull
	private String username;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
			message = "Password must contain at least one letter, one number, one special character and charcters more than 8")
	@NotNull @NotBlank
	private String password;
}
