package com.ecommerce.retail_electronicsapp.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "sellers")
@NoArgsConstructor
public class Seller extends User {

	@OneToOne
	private Address address;
	@OneToMany
	private List<Product> products=new ArrayList<>();
	
}
