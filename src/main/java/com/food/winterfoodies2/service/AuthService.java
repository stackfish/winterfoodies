package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.*;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}
