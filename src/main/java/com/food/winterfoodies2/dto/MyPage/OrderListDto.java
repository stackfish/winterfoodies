package com.food.winterfoodies2.dto.MyPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto {
    private Long id;
    private Long storeId;
    private String storeName;
    private String storeImage;
    private double storeRating;
    private LocalDateTime orderTime;
    private int totalPrice;
    private List<OrderItemDto> items;
    private Boolean hasReview;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemDto {
        private Long itemId;
        private String itemName;
        private int quantity;
    }
}