package com.ecommerce.retail_electronicsapp.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "customers")
@NoArgsConstructor
public class Customer extends User {
	
	@OneToMany
	private List<Address> addresses;
}
