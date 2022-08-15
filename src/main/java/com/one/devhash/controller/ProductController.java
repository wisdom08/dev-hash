package com.one.devhash.controller;

import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Imagefile;
import com.one.devhash.domain.Product;
import com.one.devhash.domain.ProductStatus;
import com.one.devhash.dto.product.ProductResponseDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.repository.ImagefileRepository;
import com.one.devhash.service.ProductService;
import com.one.devhash.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
	private final ProductService productService;
	private final S3Service s3Service;
	private final ImagefileRepository imagefileRepository;

	// 상품 전체 조회
	@GetMapping
	public CommonResponse<List<ProductResponseDto>> getProductList() {
		List<ProductResponseDto> products = productService.getProductList();
		return ApiUtils.success(200, products);
	}

	// 특정 상품 조회
	@GetMapping("/{productId}")
	public CommonResponse<ProductResponseDto> getProduct(@PathVariable Long productId) {
		ProductResponseDto productDto = productService.getProduct(productId);
		return ApiUtils.success(200, productDto);
	}

	// 상품 등록
	@PostMapping
	@Transactional
	public CommonResponse<Product> createProduct(@RequestParam HashMap data, MultipartFile[] productImage) throws IOException {	//, @Authentication 형태 user
		Product product = productService.createProduct(data); //, user
		if(productImage != null) {
			s3Service.uploadFile(productImage, String.valueOf(ImageTarget.PRODUCT), product.getProductId());
		}
		return ApiUtils.success(200, product);
	}

	// 상품 수정
	@PatchMapping("/{productId}")
	public CommonResponse<Product> updateProduct(@PathVariable Long productId, @RequestParam HashMap data,
												 @RequestParam ProductStatus productStatus) {
		Product product = productService.updateProduct(productId, data, productStatus);
		return ApiUtils.success(200, product);
	}

	// 상품 삭제
	@DeleteMapping("/{productId}")
	public CommonResponse<Long> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ApiUtils.success(200, productId);
	}
}