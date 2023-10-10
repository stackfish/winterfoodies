package com.food.winterfoodies2.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.food.winterfoodies2.entity.Snack;
import com.food.winterfoodies2.repository.SnackRepository;
import com.food.winterfoodies2.service.SnackService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SnackServiceImpl implements SnackService {
    private final SnackRepository snackRepository;
    private final WebClient webClient;

    public SnackServiceImpl(SnackRepository snackRepository, @Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoApiKey) {
        this.snackRepository = snackRepository;
        this.webClient = WebClient
                .builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
                .build();
    }

    public Mono<List<Snack>> getNearbySnacks(double lat, double lon) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/keyword.json")
                        .queryParam("query", "간식")
                        .queryParam("y", lat)
                        .queryParam("x", lon)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> {
                    List<Snack> snacks = new ArrayList<>();
                    if (response != null) {
                        response.get("documents").forEach(jsonNode -> {
                            Snack snack = new Snack(jsonNode.get("id").asText(),
                                    jsonNode.get("place_name").asText(),
                                    jsonNode.get("address_name").asText(),
                                    jsonNode.get("phone").asText(),
                                    jsonNode.get("distance").asDouble(),
                                    jsonNode.get("x").asDouble(),
                                    jsonNode.get("y").asDouble());
                            snacks.add(snack);
                        });
                    }

                    snacks.sort(Comparator.comparingDouble(Snack::getDistance));
                    List<Snack> topSnacks = snacks.subList(0, Math.min(snacks.size(), 5));

                    for (int i = 0; i < topSnacks.size(); i++) {
                        topSnacks.get(i).setRanking(i + 1);
                    }

                    return topSnacks;
                });
    }
}
