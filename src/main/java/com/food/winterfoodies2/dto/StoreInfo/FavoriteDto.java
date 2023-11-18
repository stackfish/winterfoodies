package com.food.winterfoodies2.dto.StoreInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {
    private boolean favorite;
    private Long storeId;
}
