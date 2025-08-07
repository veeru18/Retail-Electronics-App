package com.ecommerce.retail_electronicsapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.retail_electronicsapp.entity.Address;
import com.ecommerce.retail_electronicsapp.entity.Contact;
import com.ecommerce.retail_electronicsapp.requestdto.AddressRequest;
import com.ecommerce.retail_electronicsapp.requestdto.ContactRequest;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

public interface ContactAddressService {

	ResponseEntity<ResponseStructure<Address>> addAddressToUser(AddressRequest addressRequest);

	ResponseEntity<ResponseStructure<List<Contact>>> addContactsToAddress(int addressId, List<ContactRequest> contactRequests);

	ResponseEntity<ResponseStructure<List<Address>>> fetchAddresses();

	ResponseEntity<ResponseStructure<Address>> updateAddress(int addressId, AddressRequest addressRequest);

	ResponseEntity<ResponseStructure<Contact>> updateContact(int contactId, ContactRequest contactRequest);

}
