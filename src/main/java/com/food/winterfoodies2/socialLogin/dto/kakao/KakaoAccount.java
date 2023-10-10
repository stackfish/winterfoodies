package com.food.winterfoodies2.socialLogin.dto.kakao;

import lombok.Data;

@Data
public class KakaoAccount {
    private Boolean has_email;
    private Boolean email_needs_agreement;
    private Boolean is_email_valid;
    private Boolean is_email_verified;
    private String email;
    private Profile profile; // Add this line

    @Data
    public static class Profile {
        private String nickname;
        // Add any other fields that you need
    }
}