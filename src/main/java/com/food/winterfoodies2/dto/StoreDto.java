package com.food.winterfoodies2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private Long Id;
    private String categoryName;
    private String picture;
    private String name;
    private float rating;
    private String address;
    private int distance;
    private int ranking;
    private int reviewCount;
    private int salesVolume;

    // getters and setters
}