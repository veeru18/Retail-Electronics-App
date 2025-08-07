package com.ecommerce.retail_electronicsapp.service.impl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.retail_electronicsapp.entity.Image;
import com.ecommerce.retail_electronicsapp.enums.ImageType;
import com.ecommerce.retail_electronicsapp.exceptions.IllegalAccessRequestExcpetion;
import com.ecommerce.retail_electronicsapp.repository.ImageRepository;
import com.ecommerce.retail_electronicsapp.repository.ProductRepository;
import com.ecommerce.retail_electronicsapp.service.ImageService;
import com.ecommerce.retail_electronicsapp.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService{

	private ResponseStructure<String> simpleResponseStructure;
	private ImageRepository imageRepository;
	private ProductRepository productRepository;
	@Override
	public ResponseEntity<ResponseStructure<String>> addProductImage(int productId, String imageType, MultipartFile image) throws IOException  {
		if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
			throw new IllegalAccessRequestExcpetion("user is not authenticated");
		if(!productRepository.existsById(productId))
			throw new RuntimeException();

		if(imageType.equalsIgnoreCase("cover")) {
			System.out.println("Its cover");
			if(imageRepository.existsByProductIdAndImageType(productId, ImageType.COVER)) {
				System.out.println("cover exists");
				return imageRepository.findByProductIdAndImageType(productId,ImageType.COVER).map(im->{
					im.setImageType(ImageType.OTHER);
					imageRepository.save(im);
					Image i = new Image();
					try {
						i=i.setImageBytes(image.getBytes()).setImageType(ImageType.COVER)
							.setProductId(productId).setContentType(image.getContentType());
					} catch (IOException e) {
						e.printStackTrace();
					}
					i=imageRepository.save(i);
					return ResponseEntity.ok(simpleResponseStructure.setMessage("Image Added with id : ")
							.setStatusCode(HttpStatus.OK.value())
							.setData(i.getImageId()));
				}).orElseThrow(()-> new RuntimeException());			
			}
			else {
				System.out.println("cover doesnt exists");
				Image i = new Image();
				i=i.setImageBytes(image.getBytes()).setImageType(ImageType.COVER).setProductId(productId).setContentType(image.getContentType());
				i=imageRepository.save(i);
				return ResponseEntity.ok(simpleResponseStructure.setMessage("Image Added with id : "+i.getImageId())
						.setStatusCode(HttpStatus.OK.value())
						.setData(i.getImageId()));
			}
		}
		if(imageType.equalsIgnoreCase("other")) {
			System.out.println("Other type");
			Image i = new Image();
			i.setImageBytes(image.getBytes()).setImageType(ImageType.OTHER).setProductId(productId).setContentType(image.getContentType());
			i=imageRepository.save(i);
			return ResponseEntity.ok(simpleResponseStructure.setMessage("Image Added with id : "+i.getImageId()).setStatusCode(HttpStatus.OK.value()));
		}

		else {
			throw new RuntimeException("Image is of neither Types");
		}

	}

	@Override
	public ResponseEntity<byte[]> fetchImage(String imageId) {
		return imageRepository.findById(imageId).map(image->{
			return ResponseEntity.ok().contentLength(image.getImageBytes().length).contentType(MediaType.valueOf(image.getContentType())).body(image.getImageBytes());
		}).orElseThrow();
	}

}