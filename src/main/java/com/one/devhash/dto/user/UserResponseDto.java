package com.one.devhash.dto.user;

import com.one.devhash.domain.User;
import com.one.devhash.dto.product.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseDto {
	private Long userId;
	private String userName;
	private List<ProductResponseDto> products;

	@Builder
	public UserResponseDto(User user, List<ProductResponseDto> products) {
		this.userId = user.getId();
		this.userName = user.getUserName();
		this.products = products;
	}
}
