package com.food.winterfoodies2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "price")
    private Integer price;

//    @Column(name = "food_id")
//    private String foodId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    // getters and setters
}
