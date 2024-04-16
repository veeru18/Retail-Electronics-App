package com.ecommerce.retail_electronicsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.retail_electronicsapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

}
