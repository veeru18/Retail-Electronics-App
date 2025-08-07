package com.ecommerce.retail_electronicsapp.requestdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SearchFilters {

	private int minPrice;
	private int maxPrice;
	private int discount;
	private int rating;
	private String category;
	private String availability;
}
