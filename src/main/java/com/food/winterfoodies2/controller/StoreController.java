package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.ApiResponseSuccessDto;
import com.food.winterfoodies2.dto.StoreDto;
import com.food.winterfoodies2.service.CategoryService;
import com.food.winterfoodies2.service.StoreService;
import com.food.winterfoodies2.service.StoreSortingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/main/menu-detail")
@AllArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreSortingService storeSortingService;
    private final CategoryService categoryService; // Added

    @GetMapping("/{categoryId}/nearby")
    public ResponseEntity<ApiResponseSuccessDto> getNearbyStoresInCategory(@PathVariable Long categoryId,
                                                                           @RequestParam Double latitude,
                                                                           @RequestParam Double longitude) {
        List<StoreDto> stores = storeService.getNearbyStoresInCategory(categoryId, latitude, longitude);
        String categoryName = categoryService.getCategoryById(categoryId).getName(); // Added
        return ResponseEntity.ok(new ApiResponseSuccessDto(categoryName, stores));
    }

    @GetMapping("/{categoryId}/nearby/proximity")
    public ResponseEntity<ApiResponseSuccessDto> getNearbyStoresInCategoryByProximity(@PathVariable Long categoryId,
                                                                                      @RequestParam Double latitude,
                                                                                      @RequestParam Double longitude) {
        List<StoreDto> stores = storeService.getNearbyStoresInCategory(categoryId, latitude, longitude);
        stores = storeSortingService.sortStoresByProximity(stores);
        String categoryName = categoryService.getCategoryById(categoryId).getName(); // Added
        return ResponseEntity.ok(new ApiResponseSuccessDto(categoryName, stores));
    }

    @GetMapping("/{categoryId}/nearby/review-count")
    public ResponseEntity<ApiResponseSuccessDto> getNearbyStoresInCategoryByReviewCount(@PathVariable Long categoryId,
                                                                                        @RequestParam Double latitude,
                                                                                        @RequestParam Double longitude) {
        List<StoreDto> stores = storeService.getNearbyStoresInCategory(categoryId, latitude, longitude);
        stores = storeSortingService.sortStoresByReviewCount(stores);
        String categoryName = categoryService.getCategoryById(categoryId).getName(); // Added
        return ResponseEntity.ok(new ApiResponseSuccessDto(categoryName, stores));
    }

    @GetMapping("/{categoryId}/nearby/rating")
    public ResponseEntity<ApiResponseSuccessDto> getNearbyStoresInCategoryByRating(@PathVariable Long categoryId,
                                                                                   @RequestParam Double latitude,
                                                                                   @RequestParam Double longitude) {
        List<StoreDto> stores = storeService.getNearbyStoresInCategory(categoryId, latitude, longitude);
        stores = storeSortingService.sortStoresByRating(stores);
        String categoryName = categoryService.getCategoryById(categoryId).getName(); // Added
        return ResponseEntity.ok(new ApiResponseSuccessDto(categoryName, stores));
    }

    @GetMapping("/{categoryId}/nearby/sales-volume")
    public ResponseEntity<ApiResponseSuccessDto> getNearbyStoresInCategoryBySalesVolume(@PathVariable Long categoryId,
                                                                                        @RequestParam Double latitude,
                                                                                        @RequestParam Double longitude) {
        List<StoreDto> stores = storeService.getNearbyStoresInCategory(categoryId, latitude, longitude);
        stores = storeSortingService.sortStoresBySalesVolume(stores);
        String categoryName = categoryService.getCategoryById(categoryId).getName();
        return ResponseEntity.ok(new ApiResponseSuccessDto(categoryName, stores));
    }

}
