package com.food.winterfoodies2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private List<OrderItemDto> items;
    private int totalPrice;

    // Inner class for order items
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemDto {
        private Long userId;
        private Long productId;
        private String itemName;
        private Long price;
        private Integer quantity;
    }
}