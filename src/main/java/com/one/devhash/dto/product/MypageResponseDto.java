package com.one.devhash.dto.product;

import com.one.devhash.domain.User;
import com.one.devhash.dto.product.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MypageResponseDto {
	private Long userId;
	private String userName;
	private List<ProductResponseDto> products;
	private List<ProductResponseDto> wishProducts;

	@Builder
	public MypageResponseDto(User user, List<ProductResponseDto> products, List<ProductResponseDto> wishProducts) {
		this.userId = user.getId();
		this.userName = user.getUserName();
		this.products = products;
		this.wishProducts = wishProducts;
	}
}