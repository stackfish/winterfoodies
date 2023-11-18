package com.food.winterfoodies2.dto.StoreInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoMenuApiResponseSuccessDto {
    private String storeName;
    private String rating;
    private boolean favorites;
    private List<MenuItemDto> menu;
    private String imageUrl;
    // getters and setters
    public StoreInfoMenuApiResponseSuccessDto(List<MenuItemDto> menu) {
        this.menu = menu;
    }
}
