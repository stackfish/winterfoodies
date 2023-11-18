package com.food.winterfoodies2.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCodeDto {
    private String phoneNumber;
    private String authCode;
}