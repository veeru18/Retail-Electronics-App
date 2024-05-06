package com.ecommerce.retail_electronicsapp.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

public interface ImageService {

	ResponseEntity<ResponseStructure<String>> addProductImage(int productId, String imageType, MultipartFile image) throws IOException;
	ResponseEntity<byte[]> fetchImage(String imageId);
	
}
