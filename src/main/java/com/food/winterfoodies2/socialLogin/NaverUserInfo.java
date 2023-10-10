package com.food.winterfoodies2.socialLogin;

import com.food.winterfoodies2.socialLogin.dto.naver.NaverUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class NaverUserInfo {
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String USER_INFO_URI;

    public NaverUserInfoResponse getUserInfo(String token) {
        String uri = USER_INFO_URI;

        Mono<NaverUserInfoResponse> response = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(NaverUserInfoResponse.class);

        return response.block();
    }
}