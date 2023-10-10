package com.food.winterfoodies2.service;

import com.food.winterfoodies2.entity.Snack;
import com.food.winterfoodies2.entity.Store;

import java.util.List;

public interface StoreService {
    List<Store> getStoresByProximity(double latitude, double longitude,int categoryId);
    List<Store> getStoresBySalesVolume(double latitude, double longitude,int categoryId);
    List<Store> getStoresByReviewCount(double latitude, double longitude,int categoryId);
    List<Store> getStoresByRating(double latitude, double longitude, int categoryId);
    void updateOrInsertStore(Snack snack);
}