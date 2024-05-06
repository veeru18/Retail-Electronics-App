package com.ecommerce.retail_electronicsapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.retail_electronicsapp.requestdto.ProductRequest;
import com.ecommerce.retail_electronicsapp.requestdto.SearchFilters;
import com.ecommerce.retail_electronicsapp.responsedto.ProductResponse;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

public interface ProductService {

	ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest);
	ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(int productId,ProductRequest productRequest);
	ResponseEntity<ResponseStructure<List<ProductResponse>>> fetchSellerAllProducts();
	ResponseEntity<ResponseStructure<ProductResponse>> fetchProduct(int productId);
	ResponseEntity<ResponseStructure<List<ProductResponse>>> fetchProductsByFilter(SearchFilters filters);
}
