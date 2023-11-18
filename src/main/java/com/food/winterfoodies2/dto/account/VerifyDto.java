package com.food.winterfoodies2.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyDto {
    private String phoneNumber;
    private String authCode;
}
