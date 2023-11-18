package com.food.winterfoodies2.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {
    private String username;
    private String newPassword;
}
