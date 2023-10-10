package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.entity.Store;
import com.food.winterfoodies2.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/proximity")
    public List<Store> getStoresByProximity(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int categoryId) {
        return storeService.getStoresByProximity(latitude, longitude, categoryId);
    }

    @GetMapping("/sales-volume")
    public List<Store> getStoresBySalesVolume(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int categoryId) {
        return storeService.getStoresBySalesVolume(latitude, longitude, categoryId);
    }

    @GetMapping("/review-count")
    public List<Store> getStoresByReviewCount(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int categoryId) {
        return storeService.getStoresByReviewCount(latitude, longitude, categoryId);
    }

    @GetMapping("/rating")
    public List<Store> getStoresByRating(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int categoryId) {
        return storeService.getStoresByRating(latitude, longitude, categoryId);
    }
}