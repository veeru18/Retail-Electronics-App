package com.ecommerce.retail_electronicsapp.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.ecommerce.retail_electronicsapp.enums.ImageType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(collection = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Image {

	@MongoId
	private String imageId;
	private ImageType imageType;
	private byte[] imageBytes;
	private int productId;
	private String contentType;
	
	public Image setImageId(String imageId) {
		
		this.imageId = imageId;
		return this;
	}
	public Image setImageType(ImageType imageType) {
		this.imageType = imageType;
		return this;
	}
	public Image setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
		return this;
	}
	public Image setProductId(int productId) {
		this.productId = productId;
		return this;
	}
	public Image setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	
}
