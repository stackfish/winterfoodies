package com.food.winterfoodies2.service.impl;

import com.food.winterfoodies2.entity.Snack;
import com.food.winterfoodies2.entity.Store;
import com.food.winterfoodies2.repository.StoreRepository;
import com.food.winterfoodies2.service.StoreService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getStoresByProximity(double latitude, double longitude, int categoryId) {
        return storeRepository.findStoresInProximityAndCategory(latitude, longitude, categoryId);
    }

    @Override
    public List<Store> getStoresBySalesVolume(double latitude, double longitude, int categoryId) {
        List<Store> nearbyStores = storeRepository.findStoresInProximityAndCategory(latitude, longitude, categoryId);
        return nearbyStores.stream()
                .sorted(Comparator.comparing(Store::getSalesVolume).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> getStoresByReviewCount(double latitude, double longitude, int categoryId) {
        List<Store> nearbyStores = storeRepository.findStoresInProximityAndCategory(latitude, longitude, categoryId);
        return nearbyStores.stream()
                .sorted(Comparator.comparing(Store::getReviewCount).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> getStoresByRating(double latitude, double longitude, int categoryId) {
        List<Store> nearbyStores = storeRepository.findStoresInProximityAndCategory(latitude, longitude, categoryId);
        return nearbyStores.stream()
                .sorted(Comparator.comparing(Store::getRating).reversed())
                .collect(Collectors.toList());
    }
    @Override
    public void updateOrInsertStore(Snack snack) {
        Optional<Store> optionalStore = storeRepository.findById(Long.valueOf(snack.getStoreId()));
        if (optionalStore.isPresent()) {
            // update existing store
            Store store = optionalStore.get();
            store.setName(snack.getName());
            store.setAddress(snack.getAddress());
            // ...
            storeRepository.save(store);
        } else {
            // insert new store
            Store store = new Store();
            store.setStoreId(snack.getStoreId());
            store.setName(snack.getName());
            store.setAddress(snack.getAddress());
            // ...
            storeRepository.save(store);
        }
    }
}