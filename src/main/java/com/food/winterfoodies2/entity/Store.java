package com.food.winterfoodies2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String storeId;
    private String name;
    private String address;
    private double rating;
    private int salesVolume = 0;
    private int reviewCount = 0;
    private String imageUrl;
    private double latitude;
    private double longitude;

    @ManyToOne
    private Category category;

    // getters and setters
}
