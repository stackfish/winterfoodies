package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.ApiResponseDto;
import com.food.winterfoodies2.dto.ApiResponseErrorDto;
import com.food.winterfoodies2.dto.ApiResponseSuccessDto;
import com.food.winterfoodies2.dto.account.RegisterDto;
import com.food.winterfoodies2.dto.account.SendAuthCodeDto;
import com.food.winterfoodies2.dto.account.VerifyDto;
import com.food.winterfoodies2.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin({"*"})
@RestController
@RequestMapping({"/api/auth"})
@AllArgsConstructor
public class AuthSessionController {
    private AuthService authService;

    @PostMapping("/sendAuthCode")
    public ResponseEntity<ApiResponseSuccessDto> sendAuthCode(@RequestBody SendAuthCodeDto sendAuthCodeDto, HttpSession session) {
        String response = authService.sendAuthCode(sendAuthCodeDto);
        session.setAttribute("authCodeSent", true);
        ApiResponseSuccessDto apiResponse = new ApiResponseSuccessDto(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponseDto> verifyAuthCode(@RequestBody VerifyDto verifyDto, HttpSession session) {
        Boolean authCodeSent = (Boolean) session.getAttribute("authCodeSent");
        if (authCodeSent == null || !authCodeSent) {
            ApiResponseDto apiResponse = new ApiResponseErrorDto("Auth code not sent", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        boolean isVerified = authService.verifyAuthCode(verifyDto.getPhoneNumber());
        ApiResponseDto apiResponse;

        if (isVerified) {
            session.setAttribute("phoneNumber", verifyDto.getPhoneNumber());
            session.setAttribute("verified", true);
            apiResponse = new ApiResponseSuccessDto("Auth code verified successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse = new ApiResponseErrorDto("Invalid auth code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> register(@RequestBody RegisterDto registerDto, HttpSession session) {
        Boolean verified = (Boolean) session.getAttribute("verified");
        if (verified == null || !verified) {
            ApiResponseDto apiResponse = new ApiResponseErrorDto("Not verified", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        String phoneNumber = (String) session.getAttribute("phoneNumber");
        if(phoneNumber != null) {
            registerDto.setPhoneNumber(phoneNumber);
        }
        String response = authService.register(registerDto);
        ApiResponseSuccessDto apiResponse = new ApiResponseSuccessDto(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}

