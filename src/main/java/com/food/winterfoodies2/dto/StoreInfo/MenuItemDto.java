package com.food.winterfoodies2.dto.StoreInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemDto {
    private Long foodId;
    private String menuName;
    private int price;
    // getter and setter
}