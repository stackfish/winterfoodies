package com.food.winterfoodies2.dto.account;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindIdDto {
    private String phoneNumber;
    private String authCode;
}