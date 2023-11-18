package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.account.FindIdDto;
import com.food.winterfoodies2.dto.account.FindPasswordDto;
import com.food.winterfoodies2.dto.account.SendAuthCodeDto;
import com.food.winterfoodies2.dto.account.FindIdResponseDto;
import com.food.winterfoodies2.dto.account.JwtAuthResponse;
import com.food.winterfoodies2.dto.account.LoginDto;
import com.food.winterfoodies2.dto.account.RegisterDto;
import com.food.winterfoodies2.entity.User;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
    boolean verifyAuthCode(String phoneNumber);

    FindIdResponseDto findId(FindIdDto findIdDto);
    User verifyUser(FindPasswordDto findPasswordDto);
    String resetPassword(Long userId, String newPassword);
    String sendAuthCode(SendAuthCodeDto sendAuthCodeDto);

    String sendAuthCodeFind(SendAuthCodeDto sendAuthCodeDto);
}
