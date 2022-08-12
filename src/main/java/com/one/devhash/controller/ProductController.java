package com.one.devhash.controller;

import com.one.devhash.domain.Product;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public CommonResponse<List<Product>> getProductList() {
		List<Product> products = productService.getProductList();
		return ApiUtils.success(200, products);
	}

	@GetMapping("/{productId}")
	public CommonResponse<Product> getProduct(@PathVariable Long productId) {
		Product product = productService.getProduct(productId);
		return ApiUtils.success(200, product);
	}

	@PostMapping
	public CommonResponse<Product> createProduct(@RequestParam HashMap data) {	//MultipartFile image, @Authentication 형태 user
		Product product = productService.createProduct(data); //, user
		return ApiUtils.success(200, product);
	}
}