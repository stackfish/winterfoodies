package com.food.winterfoodies2.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverAuthRequest {
    private String code;
    private String state;

    // getters and setters
}
