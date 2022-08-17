package com.one.devhash.service;

import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Product;
import com.one.devhash.domain.User;
import com.one.devhash.dto.product.ProductResponseDto;
import com.one.devhash.dto.product.MypageResponseDto;
import com.one.devhash.global.error.exception.EntityNotFoundException;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.repository.ImagefileRepository;
import com.one.devhash.repository.ProductRepository;
import com.one.devhash.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageService {
	private final UserService userService;
	private final ProductRepository productRepository;
	private final ImagefileRepository imagefileRepository;
	private final WishRepository wishRepository;

	public MypageResponseDto getMypage() {
		User user = userService.findByUserName(getCurrentUsername());
		List<Product> products = user.getProducts();
		List<ProductResponseDto> productDtos = products.stream()
				.map(p -> new ProductResponseDto(p, imagefileRepository.findAllByTargetId(ImageTarget.PRODUCT, p.getProductId()),
						wishRepository.countByProductProductId(p.getProductId())))
				.collect(Collectors.toList());
		List<ProductResponseDto> wishProducts = wishRepository.findAllByUserId(user.getId()).stream()
				.map(w -> productRepository.findByProductId(w.getWishId())
						.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT)))
				.map(p -> new ProductResponseDto(p, imagefileRepository.findAllByTargetId(ImageTarget.PRODUCT, p.getProductId()),
						wishRepository.countByProductProductId(p.getProductId())))
				.collect(Collectors.toList());
		return new MypageResponseDto(user, productDtos, wishProducts);
	}

	private static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserDetails)principal).getUsername();
	}
}
