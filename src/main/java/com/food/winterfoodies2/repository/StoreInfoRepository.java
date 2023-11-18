package com.food.winterfoodies2.repository;

import com.food.winterfoodies2.entity.StoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreInfoRepository extends JpaRepository<StoreInfo, Long> {
}
