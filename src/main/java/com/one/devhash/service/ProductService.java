package com.one.devhash.service;

import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Product;
import com.one.devhash.domain.ProductStatus;
import com.one.devhash.domain.User;
import com.one.devhash.dto.product.ProductRequestDto;
import com.one.devhash.dto.product.ProductResponseDto;
import com.one.devhash.global.error.exception.EntityNotFoundException;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.repository.ImagefileRepository;
import com.one.devhash.repository.ProductRepository;
import com.one.devhash.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final UserService userService;
	private final ImagefileRepository imagefileRepository;
	private final WishRepository wishRepository;

	public List<ProductResponseDto> getProductList(Pageable pageable) {
		List<Product> products = productRepository.findAllByOrderByCreatedAtDesc(pageable).getContent();
		return products.stream()
				.map(p -> new ProductResponseDto(p, imagefileRepository.findAllByTargetId(ImageTarget.PRODUCT, p.getProductId()),
						wishRepository.countByProductProductId(p.getProductId())))
				.toList();
	}

	public ProductResponseDto getProduct(Long productId) {
		Product product = productRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT));
		int wishCount = wishRepository.countByProductProductId(productId);
		return new ProductResponseDto(product, imagefileRepository.findAllByTargetId(ImageTarget.PRODUCT, productId), wishCount);
	}

	public List<ProductResponseDto> getMypageProduct(Long userId) {
		List<Product> products = productRepository.findAllByUserId(userId);
		return products.stream()
				.map(p -> new ProductResponseDto(p, imagefileRepository.findAllByTargetId(ImageTarget.PRODUCT, p.getProductId()),
						wishRepository.countByProductProductId(p.getProductId())))
				.toList();
	}

	public Product createProduct(ProductRequestDto requestDto) {
		checkContent(requestDto);
		User user = userService.findByUserName(getCurrentUsername());
		Product product = requestDto.toProduct(user);
		productRepository.save(product);
		return product;
	}
	@Transactional
	public void updateProduct(Long productId, ProductRequestDto requestDto) {
		checkContent(requestDto);
		Product product = checkWriter(productId);
		product.update(requestDto);
	}

	@Transactional
	public void changeStatus(Long productId, ProductStatus productStatus) {
		Product product = checkWriter(productId);
		product.updateStatus(productStatus);
	}

	public void deleteProduct(Long productId) {
		checkWriter(productId);
		try {
			productRepository.deleteById(productId);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT);
		}
	}

	private static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserDetails)principal).getUsername();
	}
	public Product checkWriter(Long productId) {
		User user = userService.findByUserName(getCurrentUsername());
		Product product = productRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT));
		if(user != product.getUser()) { throw new EntityNotFoundException(ErrorCode.NOT_AUTHORIZED); }
		return product;
	}
	public void checkContent(ProductRequestDto requestDto) {
		if(requestDto.getProductTitle() == null || requestDto.getProductContent() == null) {
			throw new EntityNotFoundException(ErrorCode.INVALID_CONTENT);
		}
	}

	public Product getAProduct (Long productId) {
		return productRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT));
	}
}
