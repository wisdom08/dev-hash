package com.one.devhash.controller;

import com.one.devhash.dto.product.MypageResponseDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.MypageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MypageController {
	private final MypageService mypageService;

	@ApiOperation(value = "마이페이지 조회", notes = "내 정보, 내가 올린 상품, 내가 찜한 상품을 조회합니다.")
	@GetMapping("/api/mypage")
	public CommonResponse<MypageResponseDto> getMypage() {
		MypageResponseDto mypage = mypageService.getMypage();
		return ApiUtils.success(200, mypage);
	}
}