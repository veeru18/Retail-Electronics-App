package com.ecommerce.retail_electronicsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.retail_electronicsapp.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
