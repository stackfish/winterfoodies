package com.food.winterfoodies2.service.impl;


import com.food.winterfoodies2.dto.StoreDto;
import com.food.winterfoodies2.entity.Store;
import com.food.winterfoodies2.repository.StoreRepository;
import com.food.winterfoodies2.service.StoreSortingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class StoreSortingServiceImpl implements StoreSortingService {

    private final StoreRepository storeRepository;
    @Override
    public List<StoreDto> sortStoresByProximity(List<StoreDto> stores) {
        return assignRank(stores.stream()
                .sorted(Comparator.comparing(StoreDto::getDistance))
                .collect(Collectors.toList()));
    }

    @Override
    public List<StoreDto> sortStoresByReviewCount(List<StoreDto> stores) {
        return assignRank(stores.stream()
                .sorted(Comparator.comparing(StoreDto::getReviewCount).reversed())
                .collect(Collectors.toList()));
    }

    @Override
    public List<StoreDto> sortStoresByRating(List<StoreDto> stores) {
        return assignRank(stores.stream()
                .sorted(Comparator.comparing(StoreDto::getRating).reversed())
                .collect(Collectors.toList()));
    }

    @Override
    public List<StoreDto> sortStoresBySalesVolume(List<StoreDto> stores) {
        return assignRank(stores.stream()
                .sorted(Comparator.comparing(StoreDto::getSalesVolume).reversed())
                .collect(Collectors.toList()));
    }

    private List<StoreDto> assignRank(List<StoreDto> sortedStores) {
        IntStream.range(0, sortedStores.size())
                .forEach(i -> sortedStores.get(i).setRanking(i + 1));
        return sortedStores;
    }


}
