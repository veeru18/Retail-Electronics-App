package com.ecommerce.retail_electronicsapp.entity;

import com.ecommerce.retail_electronicsapp.enums.Priority;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class Contact {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contactId;
	private String name;
	private String email;
	private long contactNumber;
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	public Contact setName(String name) {
		this.name = name;
		return this;
	}
	public Contact setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
		return this;
	}
	public Contact setPriority(Priority priority) {
		this.priority = priority;
		return this;
	}
	public Contact setEmail(String email) {
		this.email = email;
		return this;
	}
	
}
