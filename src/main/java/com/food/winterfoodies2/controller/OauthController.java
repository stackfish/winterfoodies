package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.*;
import com.food.winterfoodies2.dto.account.JwtAuthResponse;
import com.food.winterfoodies2.dto.account.OauthCodeDto;
import com.food.winterfoodies2.service.OauthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Controller
@RequestMapping("/api/oauth")
@AllArgsConstructor
public class OauthController {

    private final OauthService oauthService;


//    @GetMapping({"/index"})
//    public String index() {
//        return "redirect:/loginForm.html";// 22
//    }http://localhost:8080/oauth/kakao/callback

   /* @GetMapping("/kakao/login")
    public void redirectKakao(HttpServletResponse httpServletResponse) throws IOException {
        String redirectUrl = "https://kauth.kakao.com/oauth/authorize?client_id=f9a7426e2123a6b9d3e94a63fb5440ce&redirect_uri=http://localhost:8080/oauth/kakao&response_type=code";
        httpServletResponse.sendRedirect(redirectUrl);
    }*/

    @PostMapping("/kakao")
    public ResponseEntity<ApiResponseSuccessDto> kakaoCallback(@RequestBody OauthCodeDto oauthCodeDto) {
        // Kakao 인증 코드를 받아서 토큰을 요청하고,
        // 그 토큰으로 사용자 정보를 가져와서 JWT 토큰을 생성하는 로직
        JwtAuthResponse jwtAuthResponse = oauthService.kakaoProcessOauth(oauthCodeDto.getCode());
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(jwtAuthResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @GetMapping("/naver")
//    public void redirectNaver(HttpServletResponse httpServletResponse) {
//        String redirectUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=9eDcDteIfsvw6oawjRq0&redirect_uri=http://localhost:3000/auth/naver&state=winterfoodies";
//        httpServletResponse.setHeader("Location", redirectUrl);
//        httpServletResponse.setStatus(302);
//    }

    @PostMapping("/naver")
    public ResponseEntity<ApiResponseSuccessDto> naverOauth(@RequestBody OauthCodeDto oauthCodeDto) {
        JwtAuthResponse jwtAuthResponse = oauthService.naverProcessOauth(oauthCodeDto.getCode());
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(jwtAuthResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
