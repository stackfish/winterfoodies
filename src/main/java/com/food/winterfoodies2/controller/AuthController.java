package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.*;
import com.food.winterfoodies2.dto.account.*;
import com.food.winterfoodies2.entity.User;
import com.food.winterfoodies2.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin({"*"})
@RestController
@RequestMapping({"/api/auth"})
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/sendAuthCodeFind")
    public ResponseEntity<ApiResponseSuccessDto> sendAuthCodeFind(@RequestBody SendAuthCodeDto sendAuthCodeDto) {
        String response = authService.sendAuthCodeFind(sendAuthCodeDto);
        ApiResponseSuccessDto apiResponse = new ApiResponseSuccessDto(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("/findId")
    public ResponseEntity<ApiResponseSuccessDto> findId(@RequestBody FindIdDto findIdDto) {
        FindIdResponseDto response = authService.findId(findIdDto);
        ApiResponseSuccessDto apiResponse = new ApiResponseSuccessDto(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/verifyUser")
    public ResponseEntity<VerifyUserDto> verifyUser(@RequestBody FindPasswordDto findPasswordDto) {
        User user = authService.verifyUser(findPasswordDto);
        VerifyUserDto verifyUserDto = new VerifyUserDto(user.getId(), user.getUsername());
        return new ResponseEntity<>(verifyUserDto, HttpStatus.OK);
    }


    @PutMapping("/resetPassword")
    public ResponseEntity<ApiResponseSuccessDto> resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequest) {
        String response = authService.resetPassword(passwordResetRequest.getUserId(), passwordResetRequest.getNewPassword());
        ApiResponseSuccessDto apiResponse = new ApiResponseSuccessDto(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponseSuccessDto> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
        ApiResponseSuccessDto apiResponse = new ApiResponseSuccessDto(jwtAuthResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
