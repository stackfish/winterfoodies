package com.food.winterfoodies2.dto.account;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class FindPasswordDto {
    private String email;
    private String username;
}