package com.one.devhash.service;

import com.one.devhash.domain.Product;
import com.one.devhash.domain.User;
import com.one.devhash.global.error.exception.EntityNotFoundException;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final UserService userService;

	public List<Product> getProductList() {
		return productRepository.findAllByOrderByCreatedAtAsc();
	}

	public Product getProduct(Long productId) {
		return productRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT));
	}

	public Product createProduct(HashMap data) { //, 형태 user
		User user = userService.findByUserName(getCurrentUsername());
		String productTitle = (String) data.get("productTitle");
		String productContent = (String) data.get("productContent");
		int productPrice = Integer.parseInt((String) data.get("productPrice"));

		Product product = Product.builder()
				.user(user)
				.productTitle(productTitle)
				.productContent(productContent)
				.productPrice(productPrice)
				.build();
		productRepository.save(product);
		return product;
	}

	private static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserDetails)principal).getUsername();
	}
}
