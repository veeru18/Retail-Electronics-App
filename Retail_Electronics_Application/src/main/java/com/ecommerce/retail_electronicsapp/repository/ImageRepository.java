package com.ecommerce.retail_electronicsapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.retail_electronicsapp.entity.Image;
import com.ecommerce.retail_electronicsapp.enums.ImageType;

public interface ImageRepository extends MongoRepository<Image, String>{

	Optional<Image> findByProductIdAndImageType(int productId, ImageType imageType);

	boolean existsByProductIdAndImageType(int productId, ImageType cover);
}
