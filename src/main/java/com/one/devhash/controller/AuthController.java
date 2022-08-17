package com.one.devhash.controller;

import com.one.devhash.dto.user.TokenResponseDto;
import com.one.devhash.dto.user.UserRequestDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public CommonResponse<?> signUp(@RequestBody @Valid UserRequestDto userRequestDto) {
        userService.signUp(userRequestDto);
        return ApiUtils.success(200, null);
    }

    @ApiOperation(value = "로그인")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signin")
    public ResponseEntity<CommonResponse<Object>> signInp(@RequestBody @Valid UserRequestDto userRequestDto) {
        TokenResponseDto tokenResponseDto = userService.signIn(userRequestDto);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken",tokenResponseDto.getAccessToken());
        responseHeaders.set("refreshToken",tokenResponseDto.getAccessToken());

        return ResponseEntity.ok().headers(responseHeaders).body(ApiUtils.success(200, null));
    }

    @ApiOperation(value = "프로필 이미지 수정")
    @PutMapping
    public CommonResponse<?> updateUserImage(MultipartFile[] imageFile) {
        userService.updateUserImage(imageFile);
        return ApiUtils.success(200, null);
    }

    /**
     * Access token 이 만료되었을 경우 프론트에서 요청할 api
     * @param  refreshTokenMap : Refresh token 을 입력받는다.
     * @return TokenResponseDto : Access token 과 Refresh token 모두 재발급해준다.
     */

    @ApiOperation(value = "토큰 재발급", notes = "refresh 토큰을 이용해 refresh 토큰과 access 토큰을 재발급 받습니다")
    @PostMapping("/reissue")
    public ResponseEntity<CommonResponse<Object>> reissueToken(@RequestBody Map<String, String> refreshTokenMap){
        TokenResponseDto tokenResponseDto = userService.reissueAccessToken(refreshTokenMap.get("refreshToken"));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken",tokenResponseDto.getAccessToken());
        responseHeaders.set("refreshToken",tokenResponseDto.getAccessToken());

        return ResponseEntity.ok().headers(responseHeaders).body(ApiUtils.success(200, null));
    }
}