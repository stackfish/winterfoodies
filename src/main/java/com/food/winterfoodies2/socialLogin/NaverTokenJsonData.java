package com.food.winterfoodies2.socialLogin;

import com.food.winterfoodies2.socialLogin.dto.naver.NaverTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NaverTokenJsonData {
    private final WebClient webClient;
    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String TOKEN_URI;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String REDIRECT_URI;
    @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
    private String GRANT_TYPE;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String CLIENT_SECRET;

    public NaverTokenResponse getToken(String code, String state) {
        String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&redirect_uri=" + REDIRECT_URI + "&code=" + code + "&state=" + state;
        System.out.println(uri);

        Mono<NaverTokenResponse> response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(NaverTokenResponse.class);

        return response.block();
    }

}
