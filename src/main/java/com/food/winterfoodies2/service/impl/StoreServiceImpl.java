package com.food.winterfoodies2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.winterfoodies2.dto.StoreDto;
import com.food.winterfoodies2.entity.Store;
import com.food.winterfoodies2.repository.StoreRepository;
import com.food.winterfoodies2.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    private static final String KAKAO_API_KEY = "f9a7426e2123a6b9d3e94a63fb5440ce"; // 카카오 API 키를 입력하세요.
    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

    private final StoreRepository storeRepository;
    private final ObjectMapper objectMapper;



    public StoreServiceImpl(StoreRepository storeRepository, ObjectMapper objectMapper) {
        this.storeRepository = storeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<StoreDto> searchStoresByName(String name) {
        List<Store> stores = storeRepository.findByNameContainingIgnoreCase(name);
        return stores.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void resetDistance() {
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            store.setDistance(-1);
            storeRepository.save(store);
        }
    }

    @Override
    public List<StoreDto> getNearbyStoresInCategory(Long categoryId, Double latitude, Double longitude) {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .filter(store -> store.getCategoryId() == categoryId)
                .filter(store -> calculateDistance(latitude, longitude, store.getLatitude(), store.getLongitude()) <= 100)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private StoreDto convertToDto(Store store) {
        StoreDto dto = new StoreDto();
        dto.setId(store.getId());
        dto.setCategoryName(store.getCategory());
        dto.setPicture(store.getImageUrl());
        dto.setName(store.getName());
        dto.setRating(store.getRating());
        dto.setAddress(store.getAddress());
        dto.setDistance(store.getDistance());
        dto.setReviewCount(store.getReviewCount());
        dto.setSalesVolume(store.getSalesVolume());
        // Assuming you have a method to calculate distance
        return dto;
    }

    private double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    @Override
    public void saveNearbyStores(Double latitude, Double longitude) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(KAKAO_API_URL)
                .queryParam("y", latitude)
                .queryParam("x", longitude)
                .queryParam("radius", 10000)
                .queryParam("query", "store")
                .queryParam("category_group_code", "FD6");

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        // API 응답을 파싱하여 Store 객체 리스트로 변환
        Random rand = new Random();

        Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        List<Map<String, Object>> documents = (List<Map<String, Object>>) responseMap.get("documents");
        List<Store> stores = new ArrayList<>();
        for (Map<String, Object> document : documents) {
            Store store = new Store();
            store.setStoreId((String) document.get("id"));
            store.setName((String) document.get("place_name"));
            store.setAddress((String) document.get("address_name"));
            // 카카오 API에서는 rating, salesVolume, reviewCount, imageUrl 정보를 제공하지 않음
            store.setLatitude(Double.parseDouble((String) document.get("y")));
            store.setLongitude(Double.parseDouble((String) document.get("x")));
            store.setCategory((String) document.get("category_name"));
            store.setPlaceUrl((String) document.get("place_url"));
            if (document.containsKey("distance")) {
                store.setDistance(Integer.parseInt((String) document.get("distance")));
            }

            // Random number between 1 and 9 for categoryId
            int randomNum = rand.nextInt(9) + 1;
            store.setCategoryId(randomNum);
            randomNum = rand.nextInt(9) + 1;
            store.setCategoryId(randomNum);

            // Random number between 1 and 100 for reviewCount
            int randomReviewCount = rand.nextInt(100) + 1;
            store.setReviewCount(randomReviewCount);

            // Random number between 1 and 1000 for salesVolume
            int randomSalesVolume = rand.nextInt(1000) + 1;
            store.setSalesVolume(randomSalesVolume);

            // Random float between 0.1 and 5.0 for rating
            float randomRating = rand.nextFloat() * (5.0f - 0.1f) + 0.1f;
            randomRating = Math.round(randomRating * 10) / 10.0f;
            store.setRating(randomRating);

            stores.add(store);
        }

        // 데이터베이스에 저장
        List<Store> savedStores = new ArrayList<>();
        for (Store newStore : stores) {
            Store savedStore;
            if (newStore.getStoreId() != null) {
                Store foundStore = storeRepository.findByStoreId(newStore.getStoreId()).orElse(null);
                if (foundStore == null) {
                    savedStore = storeRepository.save(newStore); // 새 가게면 저장
                } else {
                    // 기존 가게면 업데이트
                    foundStore.setName(newStore.getName());
                    foundStore.setAddress(newStore.getAddress());
                    foundStore.setLatitude(newStore.getLatitude());
                    foundStore.setLongitude(newStore.getLongitude());
                    foundStore.setCategory(newStore.getCategory());
                    foundStore.setPlaceUrl(newStore.getPlaceUrl());
                    if (newStore.getDistance() != 0) {
                        foundStore.setDistance(newStore.getDistance());
                    }
                    // rating, salesVolume, reviewCount, imageUrl 필드는 업데이트하지 않음
                    savedStore = storeRepository.save(foundStore);
                }
            } else {
                savedStore = storeRepository.save(newStore); // ID가 없는 새 가게면 저장
            }
            savedStores.add(savedStore);
        }
    }
}