package com.ecommerce.retail_electronicsapp.responsedto;

import java.util.List;

import com.ecommerce.retail_electronicsapp.enums.AvailabilityStatus;
import com.ecommerce.retail_electronicsapp.enums.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class ProductResponse {

	private int productId;
	private String productName;
	private String productDescription;
	private Category category;
	private int productQuantity;
	private int productPrice;
	private AvailabilityStatus availabilityStatus;
	private String coverImageLink;
	private List<String> imageLinks;
}
