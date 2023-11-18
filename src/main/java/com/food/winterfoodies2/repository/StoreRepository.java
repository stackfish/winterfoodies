package com.food.winterfoodies2.repository;

import com.food.winterfoodies2.entity.Store;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByStoreId(String storeId);

    @NotNull
    Optional<Store> findById(@NotNull Long storeId);

    List<Store> findByNameContainingIgnoreCase(String name);
}