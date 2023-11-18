package com.food.winterfoodies2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "store_info")
public class StoreInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "owner_comment")
    private String ownerComment;

    @Column(name = "business_hours")
    private String businessHours;

    @Column(name = "cooking_time")
    private String cookingTime;

    @Column(name = "description")
    private String description;

    // Store와의 관계 설정 필요
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}