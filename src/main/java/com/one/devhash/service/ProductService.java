package com.one.devhash.service;

import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Product;
import com.one.devhash.domain.ProductStatus;
import com.one.devhash.domain.User;
import com.one.devhash.dto.product.ProductResponseDto;
import com.one.devhash.global.error.exception.EntityNotFoundException;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.repository.ImagefileRepository;
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
	private final ImagefileRepository imagefileRepository;

	public List<ProductResponseDto> getProductList() {
		List<Product> products = productRepository.findAllByOrderByCreatedAtAsc();
		List<ProductResponseDto> productDto = products.stream()
				.map(p -> new ProductResponseDto(p, imagefileRepository.findAllByTargetId(ImageTarget.PRODUCT, p.getProductId())))
				.toList();
		return productDto;
	}

	public ProductResponseDto getProduct(Long productId) {
		Product product = productRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT));
		return new ProductResponseDto(product, imagefileRepository.findAllByTargetId(ImageTarget.PRODUCT, productId));
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
	public Product updateProduct(Long productId, HashMap data, ProductStatus productStatus) {
		User user = userService.findByUserName(getCurrentUsername());
		Product product = productRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT));
		if(user != product.getUser()) { throw new EntityNotFoundException(ErrorCode.NOT_AUTHORIZED); }
		String productTitle = (String) data.get("productTitle");
		String productContent = (String) data.get("productContent");
		int productPrice = Integer.parseInt((String) data.get("productPrice"));

		product.updateBuilder()
				.productTitle(productTitle)
				.productContent(productContent)
				.productPrice(productPrice)
				.productStatus(productStatus)
				.build();
		return product;
	}

	public void deleteProduct(Long productId) {
		User user = userService.findByUserName(getCurrentUsername());
		Product product = productRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT));
		if(user != product.getUser()) { throw new EntityNotFoundException(ErrorCode.NOT_AUTHORIZED); }
		try {
			productRepository.deleteById(productId);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT);
		}
	}
}
