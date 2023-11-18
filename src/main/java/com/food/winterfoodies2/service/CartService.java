package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.Cart.CartActionDto;
import com.food.winterfoodies2.dto.Cart.CartApiResponseSuccessDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CartService {
    void addItem(CartActionDto item);
    void removeItem(Long userId, Long itemId);

    Optional<CartApiResponseSuccessDto> getCart(Long userId);
    void emptyCart(Long userId);

    List<CartActionDto> getCarts(Long userId);

}

