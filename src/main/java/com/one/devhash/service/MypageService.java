package com.one.devhash.service;

import com.one.devhash.domain.ImageTarget;
import com.one.devhash.domain.Product;
import com.one.devhash.domain.User;
import com.one.devhash.dto.product.ProductResponseDto;
import com.one.devhash.dto.user.UserResponseDto;
import com.one.devhash.repository.ImagefileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageService {
	private final UserService userService;
	private final ImagefileRepository imagefileRepository;

	public UserResponseDto getMypage() {
		User user = userService.findByUserName(getCurrentUsername());
		List<Product> products = user.getProducts();
		List<ProductResponseDto> productDtos = products.stream()
				.map(p -> new ProductResponseDto(p, imagefileRepository.findAllByTargetId(ImageTarget.PRODUCT, p.getProductId())))
				.collect(Collectors.toList());
		UserResponseDto userDto = new UserResponseDto(user, productDtos);
		return userDto;
	}

	private static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserDetails)principal).getUsername();
	}
}
