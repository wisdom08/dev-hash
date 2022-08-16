package com.one.devhash.controller;

import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WishController {
	private final WishService wishService;

	@PostMapping("/api/wish/{productId}")
	public CommonResponse<?> addWish(@PathVariable Long productId) {
		wishService.isWish(productId);
		return ApiUtils.success(200, null);
	}
}