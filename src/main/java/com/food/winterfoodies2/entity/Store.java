package com.food.winterfoodies2.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String storeId; // 장소 ID
    private String name; // 장소 이름
    private String address; // 전체 주소
    private double latitude; // 위도
    private double longitude; // 경도
    private String category; // 카테고리 이름
    private String placeUrl; // 장소 상세 페이지 URL
    private int distance; // 중심 좌표에서의 거리
    private int categoryId;
    private float rating;
    private int salesVolume = 0;
    private int reviewCount = 0;
    @Column(length = 300)
    private String imageUrl;
    private int Ranking;
    private int cookingTime;

    //    @ElementCollection
//    @MapKeyColumn(name="menu_name")
//    @CollectionTable(name="menu_items", joinColumns=@JoinColumn(name="id"))
//    Map<String, FoodItem> menu = new HashMap<>(); // maps from menu name to FoodItem
    @OneToMany(mappedBy = "store")
    private List<MenuItem> menuItems;

    @ManyToMany(mappedBy = "favoriteStores")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private List<StoreInfo> storeInfos;

    @OneToMany(mappedBy = "store")
    private List<Review> reviews;
}
