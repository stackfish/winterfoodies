package com.food.winterfoodies2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.winterfoodies2.dto.StoreDto;
import com.food.winterfoodies2.entity.Store;

import java.util.List;

public interface StoreService {

    void resetDistance();
    void saveNearbyStores(Double latitude, Double longitude) throws JsonProcessingException;
    List<StoreDto> getNearbyStoresInCategory(Long categoryId, Double latitude, Double longitude);

    List<StoreDto> searchStoresByName(String name);

}
