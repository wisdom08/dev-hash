package com.one.devhash.controller;

import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Product;
import com.one.devhash.domain.ProductStatus;
import com.one.devhash.dto.product.ProductRequestDto;
import com.one.devhash.dto.product.ProductResponseDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.ProductService;
import com.one.devhash.service.S3Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
	private final ProductService productService;
	private final S3Service s3Service;

	@ApiOperation(value = "상품 전체 조회")
	@GetMapping
	public CommonResponse<List<ProductResponseDto>> getProductList(Pageable pageable) {
		List<ProductResponseDto> products = productService.getProductList(pageable);
		return ApiUtils.success(200, products);
	}

	@ApiOperation(value = "특정 상품 조회")
	@GetMapping("/{productId}")
	public CommonResponse<ProductResponseDto> getProduct(@PathVariable Long productId) {
		ProductResponseDto productDto = productService.getProduct(productId);
		return ApiUtils.success(200, productDto);
	}

	@ApiOperation(value = "상품 등록")
	@PostMapping
	@Transactional
	public CommonResponse<?> createProduct(ProductRequestDto requestDto, @RequestPart(required = false) MultipartFile[] productImage) {
		Product product = productService.createProduct(requestDto);
		if(productImage != null) {
			s3Service.uploadFile(productImage, String.valueOf(ImageTarget.PRODUCT), product.getProductId());
		}
		return ApiUtils.success(200, null);
	}

	@ApiOperation(value = "상품 수정")
	@PutMapping("/{productId}")
	public CommonResponse<Long> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto requestDto) {
		productService.updateProduct(productId, requestDto);
		return ApiUtils.success(200, productId);
	}

	@ApiOperation(value = "상품 상태 변경")
	@PatchMapping("/{productId}/{productStatus}")
	public CommonResponse<Long> changeStatus(@PathVariable Long productId,@PathVariable ProductStatus productStatus) {
		productService.changeStatus(productId, productStatus);
		return ApiUtils.success(200, productId);
	}

	@ApiOperation(value = "상품 삭제")
	@DeleteMapping("/{productId}")
	public CommonResponse<Long> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		s3Service.deleteFile(ImageTarget.PRODUCT, productId);
		return ApiUtils.success(200, productId);
	}
}