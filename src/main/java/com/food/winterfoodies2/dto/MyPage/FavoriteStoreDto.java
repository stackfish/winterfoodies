package com.food.winterfoodies2.dto.MyPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteStoreDto {
    private Long id;
    private String storeName;
    private String address;
    private int distance;
    private float rating;
    private String pictureUrl;
}