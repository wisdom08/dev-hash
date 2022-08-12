package com.one.devhash.controller;

import com.one.devhash.domain.Product;
import com.one.devhash.domain.User;
import com.one.devhash.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public List<Product> getProductList() {
		return productService.getProductList();
	}

	@GetMapping("/{productId}")
	public ResponseEntity<?> getProduct(@PathVariable Long productId) {
		try {
			return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@PostMapping
	public Product createProduct(@RequestBody HashMap<String, Object>  data, User user) {	//, @Authentication 형태 user
		return productService.createProduct(data, user); //, user
	}
}