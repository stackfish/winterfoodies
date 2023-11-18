package com.food.winterfoodies2.service.impl;

import com.food.winterfoodies2.dto.account.JwtAuthResponse;
import com.food.winterfoodies2.entity.User;
import com.food.winterfoodies2.security.JwtTokenProvider;
import com.food.winterfoodies2.service.OauthService;
import com.food.winterfoodies2.service.UserService;
import com.food.winterfoodies2.socialLogin.KakaoTokenJsonData;
import com.food.winterfoodies2.socialLogin.KakaoUserInfo;
import com.food.winterfoodies2.socialLogin.NaverTokenJsonData;
import com.food.winterfoodies2.socialLogin.NaverUserInfo;
import com.food.winterfoodies2.socialLogin.dto.kakao.KakaoTokenResponse;
import com.food.winterfoodies2.socialLogin.dto.kakao.KakaoUserInfoResponse;
import com.food.winterfoodies2.socialLogin.dto.naver.NaverTokenResponse;
import com.food.winterfoodies2.socialLogin.dto.naver.NaverUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    private final NaverTokenJsonData naverTokenJsonData;
    private final NaverUserInfo naverUserInfo;

    @Override
    public JwtAuthResponse kakaoProcessOauth(String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);

        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
        String email = userInfo.getKakao_account().getEmail();
        String id = String.valueOf(userInfo.getId());
        String nickname = userInfo.getKakao_account().getProfile().getNickname();
        log.info("회원 정보 입니다.{}",userInfo);

        userService.createUser(email, nickname ,id, "kakao");

        String role = "ROLE_USER";
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(nickname, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }

    @Override
    public JwtAuthResponse naverProcessOauth(String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        NaverTokenResponse naverTokenResponse = naverTokenJsonData.getToken(code);

        log.info("토큰에 대한 정보입니다.{}", naverTokenResponse);
        NaverUserInfoResponse userInfo = naverUserInfo.getUserInfo(naverTokenResponse.getAccess_token());
        String email = userInfo.getResponse().getEmail();
        String id = userInfo.getResponse().getId();
        String nickname = userInfo.getResponse().getNickname();
        log.info("회원 정보 입니다.{}", userInfo);

        userService.createUser(email, nickname, id, "naver");

        String role = "ROLE_USER";
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(nickname, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }
}