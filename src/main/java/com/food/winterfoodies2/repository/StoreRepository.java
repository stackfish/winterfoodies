package com.food.winterfoodies2.repository;

import com.food.winterfoodies2.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT s FROM Store s WHERE s.category.id = :categoryId AND ST_Distance_Sphere(point(:longitude, :latitude), point(s.longitude, s.latitude)) <= 5000")
    List<Store> findStoresInProximityAndCategory(double latitude, double longitude, int categoryId);
}