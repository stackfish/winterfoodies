package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.StoreDto;
import com.food.winterfoodies2.entity.Store;

import java.util.List;

public interface StoreSortingService {
    List<StoreDto> sortStoresByProximity(List<StoreDto> stores);
    List<StoreDto> sortStoresByReviewCount(List<StoreDto> stores);
    List<StoreDto> sortStoresByRating(List<StoreDto> stores);
    List<StoreDto> sortStoresBySalesVolume(List<StoreDto> stores);
}
