package com.ecommerce.retail_electronicsapp.requestdto;

import com.ecommerce.retail_electronicsapp.enums.UserRole;

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
public class UserRequest {
	@NotNull(message = "Please specify a name to be displayed")
	@NotBlank(message = "Name cannot be empty")
	private String name;
//	@Email(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$",message = "Please enter valid email with @ and . , this regex is for all kinds of email having @ and . ")
	@Email(regexp = "^[a-zA-Z0-9]+[a-zA-Z0-9._%+-]*@gmail\\.com$",message = "Please enter valid gmail with @ and .")
	@NotBlank @NotNull
	private String email;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
			message = "Password must contain at least one letter, one number, one special character and charcters more than 8")
	@NotNull @NotBlank
	private String password;
	@NotNull(message = "Please specify User role")
	private UserRole role;
	
}
