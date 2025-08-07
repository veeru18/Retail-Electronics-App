package com.ecommerce.retail_electronicsapp.entity;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.retail_electronicsapp.enums.AddressType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class Address {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressId;
	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private int pincode;
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	@OneToMany
	private List<Contact> contacts=new ArrayList<>(2);
	
	public Address setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
		return this;
	}
	public Address setStreetAddressAdditional(String streetAddressAdditional) {
		this.streetAddressAdditional = streetAddressAdditional;
		return this;
	}
	public Address setCity(String city) {
		this.city = city;
		return this;
	}
	public Address setState(String state) {
		this.state = state;
		return this;
	}
	public Address setPincode(int pincode) {
		this.pincode = pincode;
		return this;
	}
	public Address setAddressType(AddressType addressType) {
		this.addressType = addressType;
		return this;
	}
	public Address setContacts(List<Contact> contacts) {
		this.contacts = contacts;
		return this;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
}
