package com.ecommerce.retail_electronicsapp.requestdto;

import com.ecommerce.retail_electronicsapp.enums.Category;

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
public class ProductRequest {

	@NotNull @NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9,.-@#$& ]+$",message = "can contain alphanumeric in ")
	private String productName;
	@Pattern(regexp = "^[a-zA-Z0-9,.-@#$& ]+$",message = "can contain alphanumerics in description")
	private String productDescription;
	@NotNull(message = "category for product is required")
	private Category category;
	@NotNull(message = "quantity should not be empty field,enter atleast 0")
	private int productQuantity;
	@NotNull(message = "price should not be empty")
	private int productPrice;
	
}
