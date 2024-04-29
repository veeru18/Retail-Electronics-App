package com.ecommerce.retail_electronicsapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.retail_electronicsapp.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller,Integer> {

	Optional<Seller> findByUsername(String username);

}
