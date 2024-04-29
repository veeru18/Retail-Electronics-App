package com.ecommerce.retail_electronicsapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.retail_electronicsapp.entity.Address;
import com.ecommerce.retail_electronicsapp.entity.Contact;
import com.ecommerce.retail_electronicsapp.requestdto.AddressRequest;
import com.ecommerce.retail_electronicsapp.requestdto.ContactRequest;
import com.ecommerce.retail_electronicsapp.service.ContactAddressService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/re-v1")
@AllArgsConstructor
public class ContactAddressController {
	
	private ContactAddressService contactAddressService;
	
	@PostMapping("/address")
	public ResponseEntity<ResponseStructure<Address>> addAddressToUser(@RequestBody @Valid AddressRequest addressRequest,
																		@CookieValue(required=false, value = "at") String accessToken){
		return contactAddressService.addAddressToUser(addressRequest,accessToken);
	}
	@PutMapping("/address/{addressId}")
	public ResponseEntity<ResponseStructure<Address>> updateAddress(@CookieValue(required=false, value = "at") String accessToken,
																	@PathVariable int addressId,
																	@RequestBody @Valid AddressRequest addressRequest){
		return contactAddressService.updateAddress(addressId,addressRequest,accessToken);
	}
	
	@PostMapping("/contacts/{addressId}")
	public ResponseEntity<ResponseStructure<List<Contact>>> addContactsToAddress(@CookieValue(required=false, value = "at") String accessToken, 
																			@PathVariable int addressId, 
															@RequestBody @Valid @Size(max = 2) List<ContactRequest> contactRequests){
		return contactAddressService.addContactsToAddress(accessToken,addressId,contactRequests);
	}
	
	@PutMapping("/contacts/{contactId}")
	public ResponseEntity<ResponseStructure<Contact>> updateContact(@CookieValue(required=false, value = "at") String accessToken, 
																	@PathVariable int contactId,
																	@RequestBody ContactRequest contactRequest){
		return contactAddressService.updateContact(accessToken,contactId, contactRequest);
	}
	
	@GetMapping("/address")
	public ResponseEntity<ResponseStructure<List<Address>>> fetchAddress(@CookieValue(required=false, value = "at") String accessToken){
		return contactAddressService.fetchAddress(accessToken);
	}
}
