package com.food.winterfoodies2.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequestDto {
    private Long userId;
    private String newPassword;

    // getters and setters
}
