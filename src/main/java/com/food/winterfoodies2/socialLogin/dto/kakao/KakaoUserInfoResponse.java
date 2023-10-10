package com.food.winterfoodies2.socialLogin.dto.kakao;

import lombok.Data;

@Data
public class KakaoUserInfoResponse {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;
    private String role;
}
