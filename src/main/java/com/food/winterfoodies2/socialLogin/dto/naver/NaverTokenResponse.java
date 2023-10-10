package com.food.winterfoodies2.socialLogin.dto.naver;

import lombok.Data;

@Data
public class NaverTokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private long expires_in;


    // getters and setters
}