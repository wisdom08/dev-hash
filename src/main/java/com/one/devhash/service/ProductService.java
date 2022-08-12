package com.one.devhash.service;

import com.one.devhash.domain.Product;
import com.one.devhash.domain.User;
import com.one.devhash.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
	private ProductRepository productRepository;

	public List<Product> getProductList() {
		return productRepository.findAllByOrderByCreatedAtAsc();
	}

	public Product getProduct(Long productId) {
		return productRepository.findByProductId(productId)
				.orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다"));
	}

	public Product createProduct(HashMap data, User user) { //, 형태 user
		String productTitle = (String) data.get("productTitle");
		String productContent = (String) data.get("productContent");
		int productPrice = (int) data.get("productPrice");

		Product product = Product.builder()
				.user(user)
				.productTitle(productTitle)
				.productContent(productContent)
				.productPrice(productPrice)
				.build();
		productRepository.save(product);
		return product;
	}
}
