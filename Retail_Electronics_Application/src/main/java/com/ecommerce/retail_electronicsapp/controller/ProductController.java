package com.ecommerce.retail_electronicsapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.retail_electronicsapp.requestdto.ProductRequest;
import com.ecommerce.retail_electronicsapp.requestdto.SearchFilters;
import com.ecommerce.retail_electronicsapp.responsedto.ProductResponse;
import com.ecommerce.retail_electronicsapp.service.ProductService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/re-v1")
public class ProductController {
	
	private ProductService productService;

	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products/product")
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest) {
		return productService.addProduct(productRequest);
	}
	
	@PreAuthorize("hasAuthority('SELLER')")
	@GetMapping("/seller/products")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> fetchSellerAllProducts() {
		return productService.fetchSellerAllProducts();
	}
	
	@GetMapping("/products/product/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> fetchProduct(@PathVariable int productId) {
		return productService.fetchProduct(productId);
	}
	
	@PreAuthorize("hasAuthority('SELLER')")
	@PutMapping("/products/product/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(@PathVariable int productId, @RequestBody ProductRequest productRequest) {
		return productService.updateProduct(productId, productRequest);
	}
	
//	@PreAuthorize("hasAuthority('CUSTOMER')")
	// if no annotations, receives requestParams n maps it to the fields present in specified object
	@GetMapping("/products")
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> fetchProductsByFilter(SearchFilters filters) {
		return productService.fetchProductsByFilter(filters);
	}
}
