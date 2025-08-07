package com.ecommerce.retail_electronicsapp.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.retail_electronicsapp.service.ImageService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/re-v1")
public class ImageController {

	private ImageService imageService;

	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products/{productId}/images")
	public ResponseEntity<ResponseStructure<String>> addProductImage(@PathVariable int productId, String imageType,
			MultipartFile image) throws IOException{
		return imageService.addProductImage(productId,imageType,image);
	}

	@GetMapping("/product/image/{imageId}")
	public ResponseEntity<byte[]> fetchProductImage(@PathVariable String imageId){
		return imageService.fetchImage(imageId);
	}
	
}
