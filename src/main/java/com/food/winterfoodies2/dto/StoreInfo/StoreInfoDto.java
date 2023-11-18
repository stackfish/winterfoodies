package com.food.winterfoodies2.dto.StoreInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInfoDto {
    private String address;
    private String ownerComment;
    private String businessHours;
    private String cookingTime;
    private String description;
    private Boolean favorite;
    // getter and setter
}