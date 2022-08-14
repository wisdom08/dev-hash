package com.one.devhash.controller;

import com.one.devhash.dto.user.UserRequestDto;
import com.one.devhash.dto.user.TokenResponseDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public CommonResponse<?> signUp(@RequestBody @Valid UserRequestDto userRequestDto) {
        userService.signUp(userRequestDto);
        return ApiUtils.success(200, null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signin")
    public CommonResponse<TokenResponseDto> signInp(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ApiUtils.success(200, userService.signIn(userRequestDto));
    }

    /**
     * Access token 이 만료되었을 경우 프론트에서 요청할 api
     * @param  refreshTokenMap : Refresh token 을 입력받는다.
     * @return TokenResponseDto : Access token 과 Refresh token 모두 재발급해준다.
     */
    @PostMapping("/reissue")
    public TokenResponseDto reissueToken(@RequestBody Map<String, String> refreshTokenMap){
       return userService.reissueAccessToken(refreshTokenMap.get("refreshToken"));
    }
}

