package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.StoreInfo.*;

import java.util.List;

public interface StoreInfoService {
    StoreInfoMenuApiResponseSuccessDto getMenuItems(Long userId, Long storeId);
    StoreInfoDto getStoreInfo(Long storeId, Long userId);
    boolean toggleFavorite(Long userId, Long storeId);
    StoreInfoReviewApiResponseSuccessDto getStoreInfoWithReviews(Long storeId, Long userId);
}