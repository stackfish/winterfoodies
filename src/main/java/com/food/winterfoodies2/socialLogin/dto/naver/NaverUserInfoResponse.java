package com.food.winterfoodies2.socialLogin.dto.naver;

import lombok.Data;

@Data
public class NaverUserInfoResponse {
    private NaverResponse response;

    @Data
    public static class NaverResponse {
        private String id;
        private String nickname;
        private String email;
    }
}