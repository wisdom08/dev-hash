package com.one.devhash.controller;

import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.WishService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WishController {
	private final WishService wishService;

	@ApiOperation(value = "찜 추가/취소", notes = "찜 목록에 상품을 추가하거나 삭제합니다.")
	@PostMapping("/api/wish/{productId}")
	public CommonResponse<?> addWish(@PathVariable Long productId) {
		wishService.isWish(productId);
		return ApiUtils.success(200, null);
	}
}