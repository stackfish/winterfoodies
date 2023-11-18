package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.account.JwtAuthResponse;

public interface OauthService {
    JwtAuthResponse kakaoProcessOauth(String code);

    JwtAuthResponse naverProcessOauth(String code);

}
