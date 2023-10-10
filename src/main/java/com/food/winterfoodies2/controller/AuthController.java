package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.ApiResponseSuccessDto;
import com.food.winterfoodies2.dto.JwtAuthResponse;
import com.food.winterfoodies2.dto.LoginDto;
import com.food.winterfoodies2.dto.RegisterDto;
import com.food.winterfoodies2.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin({"*"})
@RestController
@RequestMapping({"/api/auth"})
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseSuccessDto> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        ApiResponseSuccessDto apiResponse = new ApiResponseSuccessDto(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseSuccessDto> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
        ApiResponseSuccessDto apiResponse = new ApiResponseSuccessDto(jwtAuthResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
