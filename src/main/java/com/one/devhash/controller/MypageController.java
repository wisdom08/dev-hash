package com.one.devhash.controller;

import com.one.devhash.dto.user.UserResponseDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MypageController {
	private final MypageService mypageService;

	@GetMapping("/api/mypage")
	public CommonResponse<UserResponseDto> getMypage() {
		UserResponseDto user = mypageService.getMypage();
		return ApiUtils.success(200, user);
	}
}