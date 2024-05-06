package com.ecommerce.retail_electronicsapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ecommerce.retail_electronicsapp.entity.Product;
import com.ecommerce.retail_electronicsapp.requestdto.SearchFilters;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductSpecification {

	private SearchFilters filters;
	
	Specification<Product> buildSpecification(){
		List<Predicate> predicates=new ArrayList<>();
		return (root,query,criteriaBuilder)->{
			if(filters.getMinPrice()>0) 
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("productPrice"), filters.getMinPrice()));
			if(filters.getMaxPrice()>0)
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("productPrice"), filters.getMaxPrice()));
			if(filters.getCategory()!=null)
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("category"), filters.getCategory()));
			if(filters.getAvailability()!=null)
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("availabilityStatus"), filters.getAvailability()));
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
