package com.one.devhash.service;

import com.one.devhash.domain.User;
import com.one.devhash.dto.chatting.RoomResponseDto;
import com.one.devhash.dto.mypage.MypageResponseDto;
import com.one.devhash.dto.product.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {
	private final UserService userService;
	private final ProductService productService;
	private final RoomService roomService;

	public MypageResponseDto getMypage() {
		User user = userService.findByUserName(getCurrentUsername());
		List<ProductResponseDto> productList = productService.getMypageProduct(user.getId());
		return MypageResponseDto.builder()
				.user(user)
				.productList(productList)
				.build();
	}

	private static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserDetails)principal).getUsername();
	}
}
