package com.food.winterfoodies2.repository;

import com.food.winterfoodies2.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {
    List<OrderList> findByUserId(Long userId);
}
