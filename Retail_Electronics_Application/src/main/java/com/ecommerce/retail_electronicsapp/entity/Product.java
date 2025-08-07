package com.ecommerce.retail_electronicsapp.entity;

import com.ecommerce.retail_electronicsapp.enums.AvailabilityStatus;
import com.ecommerce.retail_electronicsapp.enums.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@AllArgsConstructor @NoArgsConstructor
public class Product {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	private String productDescription;
	@Enumerated(EnumType.STRING)
	private Category category;
	private int productQuantity;
	private int productPrice;
	@Enumerated(EnumType.STRING)
	private AvailabilityStatus availabilityStatus;
	
	public Product setProductDescription(String productDescription) {
		this.productDescription = productDescription;
		return this;
	}
	public Product setCategory(Category category) {
		this.category = category;
		return this;
	}
	public Product setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
		return this;
	}
	public Product setProductPrice(int productPrice) {
		this.productPrice = productPrice;
		return this;
	}
	public Product setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
		return this;
	}
	public Product setProductName(String productName) {
		this.productName = productName;
		return this;
	}
	
}
