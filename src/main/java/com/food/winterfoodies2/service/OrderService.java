package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.OrderRequestDto;
import com.food.winterfoodies2.entity.OrderList;

public interface OrderService {
    OrderList createOrder(Long userId, OrderRequestDto orderRequestDto);
}