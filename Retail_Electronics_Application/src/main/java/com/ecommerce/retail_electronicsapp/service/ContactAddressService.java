package com.ecommerce.retail_electronicsapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.retail_electronicsapp.entity.Address;
import com.ecommerce.retail_electronicsapp.entity.Contact;
import com.ecommerce.retail_electronicsapp.requestdto.AddressRequest;
import com.ecommerce.retail_electronicsapp.requestdto.ContactRequest;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

public interface ContactAddressService {

	ResponseEntity<ResponseStructure<Address>> addAddressToUser(AddressRequest addressRequest, String accessToken);

	ResponseEntity<ResponseStructure<List<Contact>>> addContactsToAddress(String accessToken, int addressId, List<ContactRequest> contactRequests);

	ResponseEntity<ResponseStructure<List<Address>>> fetchAddress(String accessToken);

	ResponseEntity<ResponseStructure<Address>> updateAddress(int addressId, AddressRequest addressRequest, String accessToken);

	ResponseEntity<ResponseStructure<Contact>> updateContact(String accessToken, int contactId, ContactRequest contactRequest);

}
