package com.food.winterfoodies2.dto.account;

import com.food.winterfoodies2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class UserResponseDto {
    private String nickname;
    private String phoneNumber;

    public UserResponseDto(User user) {
        this.nickname = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
    }
}
