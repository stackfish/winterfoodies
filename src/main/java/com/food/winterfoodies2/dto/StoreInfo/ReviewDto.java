package com.food.winterfoodies2.dto.StoreInfo;

import com.food.winterfoodies2.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewDto {
    private Long id;
    private String userNickname;
    private Double rating;
    private List<OrderItem> orderedItem;
    private String content;
    private String imageUrl;
    // getter and setter
}