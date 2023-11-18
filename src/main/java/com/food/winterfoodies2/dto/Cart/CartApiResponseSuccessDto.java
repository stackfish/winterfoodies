package com.food.winterfoodies2.dto.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartApiResponseSuccessDto {
    private String status = "success";
    private String imageUrl;
    private int cookingTime;
    private String storeName;
    private List<CartActionDto> data;
    private int itemCount;
}
