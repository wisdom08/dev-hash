package com.one.devhash.dto.mypage;

import com.one.devhash.domain.User;
import com.one.devhash.dto.chatting.RoomResponseDto;
import com.one.devhash.dto.product.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MypageResponseDto {
	private User user;
	private List<ProductResponseDto> productList;

	@Builder
	public MypageResponseDto(User user, List<ProductResponseDto> productList) {
		this.user = user;
		this.productList = productList;
	}
}
