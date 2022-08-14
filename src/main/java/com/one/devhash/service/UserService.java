package com.one.devhash.service;

import com.one.devhash.domain.RefreshToken;
import com.one.devhash.domain.User;
import com.one.devhash.dto.user.TokenResponseDto;
import com.one.devhash.dto.user.UserRequestDto;
import com.one.devhash.global.error.exception.EntityNotFoundException;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.global.error.exception.InvalidValueException;
import com.one.devhash.repository.RefreshTokenRepository;
import com.one.devhash.repository.UserRepository;
import com.one.devhash.security.MyUserDetailsService;
import com.one.devhash.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService myUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long signUp(UserRequestDto signUpDto){
        String userName = signUpDto.getUserName();

        validateDuplicateUser(userName);

        String encodePw = passwordEncoder.encode(signUpDto.getUserPassword());
        return userRepository.save(User.toEntity(userName, encodePw)).getId();
    }

    @Transactional
    public TokenResponseDto signIn(UserRequestDto userRequestDto) {
        String userName = userRequestDto.getUserName();
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
        if(!passwordEncoder.matches(userRequestDto.getUserPassword(), userDetails.getPassword())){
            throw new InvalidValueException(ErrorCode.NOTEQUAL_INPUT_PASSWORD);
        }

        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        User user = findByUserName(authentication.getName());
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken token = RefreshToken.createToken(user, refreshToken);
        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(token);

        return TokenResponseDto.builder()
                .accessToken("Bearer-"+jwtTokenProvider.createAccessToken(authentication))
                .refreshToken("Bearer-"+refreshToken)
                .build();
    }

    private void validateDuplicateUser(String userName){
        userRepository.findByUserName(userName)
                .ifPresent(member -> {
                    throw new InvalidValueException(ErrorCode.USERNAME_DUPLICATION);
                });
    }

    @Transactional
    public TokenResponseDto reissueAccessToken(String refreshToken) {

        String resolveToken = resolveToken(refreshToken);
        jwtTokenProvider.validateToken(resolveToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(resolveToken);
        User user = findByUserName(authentication.getName());
        RefreshToken refreshRedisToken = refreshTokenRepository.findByUserId(user.getId()).get();

        if(!resolveToken.equals(refreshRedisToken.getToken())){
            throw new InvalidValueException(ErrorCode.NOT_EQUAL_REFRESH_TOKEN);
        }

        String newToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken newRefreshToken = RefreshToken.createToken(user, newToken);
        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(newRefreshToken);

        return TokenResponseDto.builder()
                .accessToken("Bearer-"+jwtTokenProvider.createAccessToken(authentication))
                .refreshToken("Bearer-"+newToken)
                .build();
    }

    //token 앞에 "Bearer-" 제거
    private String resolveToken(String token){
        if(token.startsWith("Bearer-"))
            return token.substring(7);
        throw new InvalidValueException(ErrorCode.NOT_VALID_REFRESH_TOKEN);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_USER));
    }
}
