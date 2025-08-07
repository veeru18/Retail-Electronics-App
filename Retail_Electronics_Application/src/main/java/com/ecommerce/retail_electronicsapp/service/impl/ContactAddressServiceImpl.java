package com.ecommerce.retail_electronicsapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.retail_electronicsapp.entity.Address;
import com.ecommerce.retail_electronicsapp.entity.Contact;
import com.ecommerce.retail_electronicsapp.entity.Customer;
import com.ecommerce.retail_electronicsapp.entity.Seller;
import com.ecommerce.retail_electronicsapp.entity.User;
import com.ecommerce.retail_electronicsapp.enums.UserRole;
import com.ecommerce.retail_electronicsapp.exceptions.AccountAceessRequestDeniedException;
import com.ecommerce.retail_electronicsapp.exceptions.AddressNotFoundException;
import com.ecommerce.retail_electronicsapp.exceptions.ContactNotFoundException;
import com.ecommerce.retail_electronicsapp.exceptions.ContactNotProvidedException;
import com.ecommerce.retail_electronicsapp.exceptions.ContactsPerAddressLimitExceededException;
import com.ecommerce.retail_electronicsapp.exceptions.CustomerAddressLimitExceededException;
import com.ecommerce.retail_electronicsapp.exceptions.CustomerAddressNotFoundException;
import com.ecommerce.retail_electronicsapp.exceptions.IllegalAccessRequestExcpetion;
import com.ecommerce.retail_electronicsapp.exceptions.SellerAddressLimitExceededException;
import com.ecommerce.retail_electronicsapp.exceptions.SellerAddressNotFoundException;
import com.ecommerce.retail_electronicsapp.repository.AddressRepository;
import com.ecommerce.retail_electronicsapp.repository.ContactRepository;
import com.ecommerce.retail_electronicsapp.repository.CustomerRepository;
import com.ecommerce.retail_electronicsapp.repository.SellerRepository;
import com.ecommerce.retail_electronicsapp.repository.UserRepository;
import com.ecommerce.retail_electronicsapp.requestdto.AddressRequest;
import com.ecommerce.retail_electronicsapp.requestdto.ContactRequest;
import com.ecommerce.retail_electronicsapp.service.ContactAddressService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactAddressServiceImpl implements ContactAddressService{

	private AddressRepository addressRepo;
	private ContactRepository contactRepo;
	private UserRepository userRepo;
	private SellerRepository sellerRepository;
	private CustomerRepository customerRepository;
	private ResponseStructure<Address> respStruct;
	private ResponseStructure<Contact> contactRespStruct;
	private ResponseStructure<List<Contact>> simpleRespStruct;
	
	@Override
	public ResponseEntity<ResponseStructure<Address>> addAddressToUser(AddressRequest addressRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!authentication.isAuthenticated()) throw new IllegalAccessRequestExcpetion("user not authenticated");
		
		User user = userRepo.findByUsername(authentication.getName()).get();
		if(user.getUserRole().name()==UserRole.SELLER.name()) {
			return sellerRepository.findByUsername(user.getUsername())
							.map(seller->{
								if(seller.getAddress()!=null)  
									throw new SellerAddressLimitExceededException("address for seller must not exceed more than 1");
								Address uniqueAddress = addressRepo.save(mapToAddress(addressRequest,new Address()));
								seller.setAddress(uniqueAddress);
								Seller uniqueSeller = sellerRepository.save(seller);
								
								return ResponseEntity.ok(respStruct.setStatusCode(HttpStatus.OK.value())
										.setMessage("successfully added address to this seller")
										.setData(uniqueSeller.getAddress()));
							}).orElseThrow(()->new AccountAceessRequestDeniedException("user is not found/invalid"));
		}
		//if not seller definitely he'll be customer 
		else {
			return customerRepository.findByUsername(user.getUsername())
					.map(customer->{
						List<Address> addresses = customer.getAddresses();
						if(addresses.size() == 5) 
							throw new CustomerAddressLimitExceededException("addresses for customer must not exceed more than 5");
						Address uniqueAddress = addressRepo.save(mapToAddress(addressRequest,new Address()));
						addresses.add(uniqueAddress);
						customer.setAddresses(addresses);
						Customer uniqueCustomer = customerRepository.save(customer);
						
						return ResponseEntity.ok(respStruct.setStatusCode(HttpStatus.OK.value())
								.setMessage("successfully added address to this seller")
								.setData(uniqueCustomer.getAddresses().get(addresses.size()-1)));
					}).orElseThrow(()->new AccountAceessRequestDeniedException("user is not found/invalid"));
		}
	}
	
	
	@Override
	public ResponseEntity<ResponseStructure<Address>> updateAddress(int addressId, AddressRequest addressRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!authentication.isAuthenticated()) throw new IllegalAccessRequestExcpetion("user not authenticated");
		
		User user = userRepo.findByUsername(authentication.getName()).get(); //directly using get() since user is authenticated now 
		if(user.getUserRole().name()==UserRole.SELLER.name()) {
			return sellerRepository.findByUsername(user.getUsername()).map(seller->{
				return addressRepo.findById(addressId).map(address->{
					Address uniqueAddress = addressRepo.save(mapToAddress(addressRequest,address));
					seller.setAddress(uniqueAddress);
					Seller uniqueSeller = sellerRepository.save(seller);
					return ResponseEntity.ok(respStruct.setStatusCode(HttpStatus.OK.value())
							.setMessage("successfully added address to this seller")
							.setData(uniqueSeller.getAddress()));
				}).orElseThrow(()-> new AddressNotFoundException("Address not found based on Id specified"));
			}).orElseThrow(()->new AccountAceessRequestDeniedException("user is not found/invalid"));
		}
		else {
			return customerRepository.findByUsername(user.getUsername()).map(customer->{
				return addressRepo.findById(addressId).map(address->{
				List<Address> addresses = new ArrayList<>();
				//only if addressId is present can update
				Address uniqueAddress = addressRepo.save(mapToAddress(addressRequest,address));
				for(Address existingAddress:customer.getAddresses()) {
					if(existingAddress.getAddressId()!=addressId) addresses.add(existingAddress);
				}
				addresses.add(uniqueAddress);
				customer.setAddresses(addresses);
				customerRepository.save(customer);
				return ResponseEntity.ok(respStruct.setStatusCode(HttpStatus.OK.value())
						.setMessage("successfully added address to this seller")
						//only if addressId is present 
						.setData(uniqueAddress));
				}).orElseThrow(()-> new AddressNotFoundException("Address not found based on Id specified"));
			}).orElseThrow(()->new AccountAceessRequestDeniedException("user is not found/invalid"));
						
		}
	}
	
	@Override
	public ResponseEntity<ResponseStructure<List<Address>>> fetchAddresses() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if(!authentication.isAuthenticated()) throw new IllegalAccessRequestExcpetion("user not authenticated");
	    
	    User user = userRepo.findByUsername(authentication.getName()).get();
	    List<Address> addresses=new ArrayList<>();
	    if(user.getUserRole().name()==UserRole.SELLER.name()) {
	        return sellerRepository.findByUsername(user.getUsername()).map(seller->{
						            if(seller.getAddress()==null)  
						                throw new SellerAddressNotFoundException("seller doesn't have any address");
						            ResponseStructure<List<Address>> respStruct = new ResponseStructure<>();
						            addresses.add(seller.getAddress());
						            return ResponseEntity.ok(respStruct.setStatusCode(HttpStatus.OK.value())
														            .setMessage("successfully added address to this seller")
														            .setData(addresses));
	        }).orElseThrow(()->new AccountAceessRequestDeniedException("user is not found/invalid"));
	    }
	    else {
	        return customerRepository.findByUsername(user.getUsername()).map(customer->{
							            if(customer.getAddresses().size() == 0) 
							                throw new CustomerAddressNotFoundException("customer doesn't have any address");
							            ResponseStructure<List<Address>> respStruct = new ResponseStructure<>();
							            return ResponseEntity.ok(respStruct.setStatusCode(HttpStatus.OK.value())
													    	            .setMessage("successfully added address to this seller")
													    	            .setData(customer.getAddresses()));
	        }).orElseThrow(()-> new AccountAceessRequestDeniedException("user is not found/invalid"));
	    }
	}
	
	@Override
	public ResponseEntity<ResponseStructure<List<Contact>>> addContactsToAddress(int addressId,List<ContactRequest> contactRequests) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(!authentication.isAuthenticated()) throw new IllegalAccessRequestExcpetion("user not authenticated");
		
//		User user = userRepo.findByUsername(authentication.getName()).get();
		if(contactRequests.size()<1 || contactRequests.size()>2) 
			throw new ContactsPerAddressLimitExceededException("not more than 2 contacts can be added for an address");
		return addressRepo.findById(addressId).map(address->{
			if(address.getContacts().size()==2 || (contactRequests.size()==2 && address.getContacts().size()==1)) 
							throw new ContactsPerAddressLimitExceededException("contacts must be atmost two per address");
			address.setContacts(mapToContacts(contactRequests,new ArrayList<>()));
			Address uniqueAddress = addressRepo.save(address);
			return ResponseEntity.ok(simpleRespStruct.setStatusCode(HttpStatus.OK.value())
					.setMessage("contact added to address specified successfully")
					.setData(uniqueAddress.getContacts()));
		}).orElseThrow(()->new AddressNotFoundException("address not found for specified Id"));
	}
	
	@Override
	public ResponseEntity<ResponseStructure<Contact>> updateContact(int contactId, ContactRequest contactRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!authentication.isAuthenticated()) throw new IllegalAccessRequestExcpetion("user not authenticated");
		return contactRepo.findById(contactId).map(contact->{
			Contact uniqueContact = contactRepo.save(mapToContact(contactRequest, contact));
			return ResponseEntity.ok(contactRespStruct.setMessage("contact updated successfully")
					.setStatusCode(HttpStatus.OK.value())
					.setData(uniqueContact));
		}).orElseThrow(()-> new ContactNotFoundException("contact not found for specified Id"));
	}

	private Address mapToAddress(AddressRequest addressRequest, Address address) {
		address.setStreetAddress(addressRequest.getStreetAddress())
				 .setStreetAddressAdditional(addressRequest.getStreetAddressAdditional())
				 .setAddressType(addressRequest.getAddressType())
				 .setCity(addressRequest.getCity())
				 .setState(addressRequest.getState())
				 .setPincode(addressRequest.getPincode());
		return address;
	}
	private Contact mapToContact(ContactRequest contactReq,Contact contact) {
		contact.setName(contactReq.getName()).setContactNumber(contactReq.getPhoneNumber())
								.setEmail(contactReq.getEmail())
									.setPriority(contactReq.getPriority());
		return contact;
	}
	private List<Contact> mapToContacts(List<ContactRequest> contactRequests, List<Contact> contacts) {
		if(contactRequests==null || contactRequests.size()==0) throw new ContactNotProvidedException("provide a contact if required to be addded to specified address");
		for(ContactRequest contactReq:contactRequests ) {
			Contact toContact = mapToContact(contactReq,new Contact());
			Contact contact = contactRepo.save(toContact);
			contacts.add(contact);
		}
		return contacts;
	}
	
}
