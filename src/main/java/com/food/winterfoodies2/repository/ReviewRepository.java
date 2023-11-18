package com.food.winterfoodies2.repository;

import com.food.winterfoodies2.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStoreId(Long storeId);

    boolean existsByUserIdAndOrderId(Long userId, Long orderId);

    List<Review> findByUserId(Long userId);

    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.user.id = :userId")
    List<Review> findByUserIdWithStore(@Param("userId") Long userId);
}
